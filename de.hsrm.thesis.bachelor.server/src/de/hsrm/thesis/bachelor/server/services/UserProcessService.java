package de.hsrm.thesis.bachelor.server.services;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.scout.commons.Base64Utility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.IntegerHolder;
import org.eclipse.scout.commons.holders.LongArrayHolder;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringHolder;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;
import org.osgi.framework.ServiceRegistration;

import de.hsrm.thesis.bachelor.server.ServerSession;
import de.hsrm.thesis.filemanagement.server.util.HashUtility;
import de.hsrm.thesis.filemanagement.shared.formdata.UserFormData;
import de.hsrm.thesis.filemanagement.shared.security.CreateUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.DeleteUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.ReadUsersPermission;
import de.hsrm.thesis.filemanagement.shared.security.RegisterUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.UnregisterUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.UpdateUserPermission;
import de.hsrm.thesis.filemanagement.shared.services.IPasswordProcessService;
import de.hsrm.thesis.filemanagement.shared.services.IUserProcessService;
import de.hsrm.thesis.filemanagement.shared.services.code.UserRoleCodeType;
import de.hsrm.thesis.filemanagement.shared.services.code.UserRoleCodeType.AdministratorCode;
import de.hsrm.thesis.filemanagement.shared.services.code.UserRoleCodeType.UserCode;

public class UserProcessService extends AbstractService implements IUserProcessService {

  private static final String ENCODING = "UTF-8";
  public static final int MIN_USERNAME_LENGTH = 3;
  private Set<String> m_users;

  @Override
  public void initializeService(ServiceRegistration registration) {
    super.initializeService(registration);
    m_users = Collections.synchronizedSet(new HashSet<String>());
  }

  @Override
  public void registerUser() throws ProcessingException {
    if (!ACCESS.check(new RegisterUserPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    m_users.add(ServerSession.get().getUserId());
//    SERVICES.getService(INotificationProcessService.class).sendRefreshBuddies();
  }

  @Override
  public void unregisterUser() throws ProcessingException {
    if (!ACCESS.check(new UnregisterUserPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    m_users.remove(ServerSession.get().getUserId());
//    SERVICES.getService(INotificationProcessService.class).sendRefreshBuddies();
  }

  @Override
  public void createUser(UserFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateUserPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    createNewUser(formData.getUsername().getValue(), formData.getPassword().getValue(), formData.getRole().getValue());
  }

  @Override
  public void deleteUser(Long[] u_id) throws ProcessingException {
    if (!ACCESS.check(new DeleteUserPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    // check that we are not deleting ourselves
    IntegerHolder holder = new IntegerHolder();
    SQL.selectInto("SELECT u_id FROM TABUSERS WHERE username = :username INTO :myId", new NVPair("myId", holder), new NVPair("username", ServerSession.get().getUserId()));
    for (Long uid : u_id) {
      if (uid.equals(holder.getValue().longValue())) {
        throw new VetoException(TEXTS.get("CannotDeleteYourself"));
      }
    }

    SQL.delete("DELETE FROM user_role WHERE u_id = :ids", new NVPair("ids", u_id));

    SQL.delete("DELETE FROM role WHERE user_creator_id = :ids", new NVPair("ids", u_id));

    SQL.delete("DELETE FROM file WHERE u_id = :ids", new NVPair("ids", u_id));

    SQL.delete("DELETE FROM TABUSERS WHERE u_id = :ids", new NVPair("ids", u_id));

    //TODO: what to do if the deleted user is still logged in somewhere?
  }

  @Override
  public void updateUser(UserFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateUserPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.update("UPDATE TABUSERS SET username = :newUsername, permission_id = :newPermId WHERE u_id = :uid",
        new NVPair("newUsername", formData.getUsername().getValue()),
        new NVPair("newPermId", isAdmin(formData.getRole().getValue()) ? AdministratorCode.ID : UserCode.ID),
        new NVPair("uid", formData.getUserId()));

    setUserRoles(formData.getUserId(), formData.getRole().getValue());

  }

  @Override
  public Object[][] getUsers() throws ProcessingException {
    if (!ACCESS.check(new ReadUsersPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    return SQL.select("SELECT u_id, username, permission_id FROM TABUSERS");
  }

  @Override
  public Set<String> getUsersOnline() throws ProcessingException {
    if (!ACCESS.check(new ReadUsersPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    return m_users;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ICode<Integer> getUserPermission(String userName) throws ProcessingException {
    IntegerHolder ih = new IntegerHolder(0);
    SQL.selectInto("SELECT permission_id FROM TABUSERS WHERE username = :username INTO :permission", new NVPair("username", userName), new NVPair("permission", ih));

    return CODES.getCodeType(UserRoleCodeType.class).getCode(ih.getValue());
  }

  @Override
  public Long[] getUserRoles(Long userId) throws ProcessingException {

    LongArrayHolder roleIds = new LongArrayHolder();
    SQL.selectInto("SELECT role_id FROM user_role WHERE u_id = :userId INTO :roleIds",
        new NVPair("userId", userId),
        new NVPair("roleIds", roleIds));

    return roleIds.getValue();
  }

  @Override
  public long getUserId(String username) throws ProcessingException {
    LongHolder userId = new LongHolder();
    SQL.selectInto("SELECT u_id FROM tabusers WHERE username = :username INTO :userId",
        new NVPair("username", username),
        new NVPair("userId", userId));
    return userId.getValue();
  }

  @Override
  public boolean createNewUser(String username, String password) throws ProcessingException {
    return createNewUser(username, password, new Long[0]);
  }

  @Override
  public boolean isAdmin(Long[] roles) throws ProcessingException {
    Long adminId = getAdminRoleId();
    for (Long l : roles) {
      if (l.equals(adminId)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean createNewUser(String username, String password, Long[] roleIds) throws ProcessingException {
    try {
      checkUsername(username);
      SERVICES.getService(IPasswordProcessService.class).checkPassword(password);

      byte[] bSalt = HashUtility.createSalt();
      byte[] bHash = HashUtility.hash(password.getBytes(ENCODING), bSalt);

      String salt = Base64Utility.encode(bSalt);
      String digest = Base64Utility.encode(bHash);

      Integer permission;
      if (roleIds != null) {
        permission = isAdmin(roleIds) ? AdministratorCode.ID : UserCode.ID;
      }
      else {
        permission = UserCode.ID;
      }

      SQL.insert("INSERT INTO TABUSERS (username, pass, salt, permission_id) VALUES (:username, :pass, :salt, :permission)",
          new NVPair("username", username),
          new NVPair("pass", digest),
          new NVPair("salt", salt),
          new NVPair("permission", permission));

      //get new user id
      Long userId = getUserId(username);

      //save user roles
      if (roleIds != null) {
        setUserRoles(userId, roleIds);
      }
      return true;
    }
    catch (NoSuchAlgorithmException e) {
      throw new ProcessingException("hash algorithm not found", e);
    }
    catch (UnsupportedEncodingException e) {
      throw new ProcessingException("unknown string encoding: " + ENCODING, e);
    }
  }

  @Override
  public void resetPassword(Long u_Id, String newPassword) throws ProcessingException {
    try {
      SERVICES.getService(IPasswordProcessService.class).checkPassword(newPassword);

      byte[] bSalt = HashUtility.createSalt();
      byte[] bHash = HashUtility.hash(newPassword.getBytes(ENCODING), bSalt);

      String salt = Base64Utility.encode(bSalt);
      String digest = Base64Utility.encode(bHash);

      SQL.update("UPDATE TABUSERS SET pass = :newPass, salt = :newSalt WHERE u_id = :uid", new NVPair("newPass", digest), new NVPair("newSalt", salt), new NVPair("uid", u_Id));
    }
    catch (NoSuchAlgorithmException e) {
      throw new ProcessingException("hash algorithm not found", e);
    }
    catch (UnsupportedEncodingException e) {
      throw new ProcessingException("unknown string encoding: " + ENCODING, e);
    }

  }

  private boolean areEqual(String pass1, String pass2, String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    byte[] bPass = Base64Utility.decode(pass1);
    byte[] bSalt = Base64Utility.decode(salt);
    byte[] bInput = HashUtility.hash(pass2.getBytes(ENCODING), bSalt);

    return Arrays.equals(bInput, bPass);
  }

  @Override
  public boolean isValidUser(String username, String password) throws ProcessingException {
    try {
      StringHolder passHolder = new StringHolder();
      StringHolder saltHolder = new StringHolder();
      SQL.selectInto("SELECT pass, salt FROM TABUSERS WHERE UPPER(USERNAME) = UPPER(:username) INTO :pass, :salt",
          new NVPair("username", username),
          new NVPair("pass", passHolder),
          new NVPair("salt", saltHolder));

      String pass = passHolder.getValue();
      String salt = saltHolder.getValue();
      if (pass == null || salt == null) {
        // user was not found: to prevent time attacks even though check the passwords
        // will always return false
        return areEqual("c29tZXRoaW5n", "dummy", "c29tZXNhbHQ=");
      }
      return areEqual(pass, password, salt);
    }
    catch (NoSuchAlgorithmException e) {
      throw new ProcessingException("hash algorithm not found", e);
    }
    catch (UnsupportedEncodingException e) {
      throw new ProcessingException("unknown string encoding: " + ENCODING, e);
    }
  }

  @Override
  public Long getRoleId(String name) throws ProcessingException {
    LongHolder idHolder = new LongHolder();
    SQL.selectInto("SELECT role_id FROM role WHERE UPPER(name) = UPPER(:rolename) INTO :id",
        new NVPair("rolename", name),
        new NVPair("id", idHolder));

    return idHolder.getValue();
  }

  @Override
  public Long getAdminRoleId() throws ProcessingException {
    return getRoleId(USER_ROLE_ADMIN);
  }

  @Override
  public Long getUserRoleId() throws ProcessingException {
    return getRoleId(USER_ROLE_USER);
  }

  @Override
  public void setUserRoles(Long userId, Long[] roleIds) throws ProcessingException {
    SQL.delete("DELETE FROM user_role WHERE u_id = :userId", new NVPair("userId", userId));

    for (Long l : roleIds) {
      SQL.insert("INSERT INTO user_role(u_id, role_id) VALUES (:userId, :roleId)",
          new NVPair("userId", userId),
          new NVPair("roleId", l));
    }
  }

  @Override
  public void checkUsername(String username) throws VetoException {
    if (StringUtility.length(username) < MIN_USERNAME_LENGTH) {
      throw new VetoException(TEXTS.get("UsernameMinLength", "" + MIN_USERNAME_LENGTH));
    }
    if (username.contains("@")) {
      throw new VetoException(TEXTS.get("UsernameSpecialChars"));
    }
  }

  @Override
  public Long getCurrentUserId() {
    return ServerSession.get().getUserNr();
  }
}
