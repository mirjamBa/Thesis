package de.hsrm.mi.storage.database.derby.server.services;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.eclipse.scout.commons.Base64Utility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.IntegerHolder;
import org.eclipse.scout.commons.holders.LongArrayHolder;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringHolder;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.rt.shared.services.common.security.IAccessControlService;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.server.util.HashUtility;
import de.hsrm.thesis.filemanagement.shared.formdata.UserFormData;
import de.hsrm.thesis.filemanagement.shared.security.CreateUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.DeleteUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.ReadUsersPermission;
import de.hsrm.thesis.filemanagement.shared.security.UpdateUserPermission;
import de.hsrm.thesis.filemanagement.shared.services.IPasswordProcessService;
import de.hsrm.thesis.filemanagement.shared.services.IStartupService;
import de.hsrm.thesis.filemanagement.shared.services.IUserProcessService;

public class UserProcessService extends AbstractService
		implements
			IUserProcessService {

	private static final String ENCODING = "UTF-8";
	public static final int MIN_USERNAME_LENGTH = 3;

	@Override
	public void createUser(UserFormData formData) throws ProcessingException {
		if (!ACCESS.check(new CreateUserPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		createNewUser(formData.getUsername().getValue(), formData.getPassword()
				.getValue(), formData.getRole().getValue());
	}

	@Override
	public void deleteUser(Long[] u_id) throws ProcessingException {
		if (!ACCESS.check(new DeleteUserPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		// check that we are not deleting ourselves
		IntegerHolder holder = new IntegerHolder();
		SQL.selectInto(
				"SELECT u_id FROM TABUSERS WHERE username = :username INTO :myId",
				new NVPair("myId", holder), new NVPair("username", ServerJob
						.getCurrentSession().getUserId()));
		for (Long uid : u_id) {
			if (uid.equals(holder.getValue().longValue())) {
				throw new VetoException(TEXTS.get("CannotDeleteYourself"));
			}
		}

		SQL.delete("DELETE FROM user_role WHERE u_id = :ids", new NVPair("ids",
				u_id));

		SQL.delete("DELETE FROM role WHERE user_creator_id = :ids", new NVPair(
				"ids", u_id));

		SQL.delete("DELETE FROM file WHERE u_id = :ids",
				new NVPair("ids", u_id));

		SQL.delete("DELETE FROM TABUSERS WHERE u_id = :ids", new NVPair("ids",
				u_id));

	}

	@Override
	public void updateUser(UserFormData formData) throws ProcessingException {
		if (!ACCESS.check(new UpdateUserPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		SQL.update(
				"UPDATE TABUSERS SET username = :newUsername WHERE u_id = :uid",
				new NVPair("newUsername", formData.getUsername().getValue()),
				new NVPair("uid", formData.getUserId()));

		setUserRoles(formData.getUserId(), formData.getRole().getValue());
		
		//clear permission cache for user
		SERVICES.getService(IAccessControlService.class).clearCacheOfUserIds(formData.getUsername().getValue());

	}

	@Override
	public Object[][] getUsers() throws ProcessingException {
		if (!ACCESS.check(new ReadUsersPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}

		return SQL.select("SELECT u_id, username FROM TABUSERS");
	}

	@Override
	public Long[] getUserRoles(Long userId) throws ProcessingException {

		LongArrayHolder roleIds = new LongArrayHolder();
		SQL.selectInto(
				"SELECT role_id FROM user_role WHERE u_id = :userId INTO :roleIds",
				new NVPair("userId", userId), new NVPair("roleIds", roleIds));

		return roleIds.getValue();
	}

	@Override
	public long getUserId(String username) throws ProcessingException {
		LongHolder userId = new LongHolder();
		SQL.selectInto(
				"SELECT u_id FROM tabusers WHERE username = :username INTO :userId",
				new NVPair("username", username), new NVPair("userId", userId));
		return userId.getValue();
	}

	@Override
	public boolean createNewUser(String username, String password,
			Long[] roleIds) throws ProcessingException {
		try {
			checkUsername(username);
			SERVICES.getService(IPasswordProcessService.class).checkPassword(
					password);

			byte[] bSalt = HashUtility.createSalt();
			byte[] bHash = HashUtility.hash(password.getBytes(ENCODING), bSalt);

			String salt = Base64Utility.encode(bSalt);
			String digest = Base64Utility.encode(bHash);

			SQL.insert(
					"INSERT INTO TABUSERS (username, pass, salt) VALUES (:username, :pass, :salt)",
					new NVPair("username", username),
					new NVPair("pass", digest), new NVPair("salt", salt)
					);

			// get new user id
			Long userId = getUserId(username);

			// save user roles
			if (roleIds != null) {
				setUserRoles(userId, roleIds);
			}
			return true;
		} catch (NoSuchAlgorithmException e) {
			throw new ProcessingException("hash algorithm not found", e);
		} catch (UnsupportedEncodingException e) {
			throw new ProcessingException("unknown string encoding: "
					+ ENCODING, e);
		}
	}

	@Override
	public void resetPassword(Long u_Id, String newPassword)
			throws ProcessingException {
		try {
			SERVICES.getService(IPasswordProcessService.class).checkPassword(
					newPassword);

			byte[] bSalt = HashUtility.createSalt();
			byte[] bHash = HashUtility.hash(newPassword.getBytes(ENCODING),
					bSalt);

			String salt = Base64Utility.encode(bSalt);
			String digest = Base64Utility.encode(bHash);

			SQL.update(
					"UPDATE TABUSERS SET pass = :newPass, salt = :newSalt WHERE u_id = :uid",
					new NVPair("newPass", digest), new NVPair("newSalt", salt),
					new NVPair("uid", u_Id));
		} catch (NoSuchAlgorithmException e) {
			throw new ProcessingException("hash algorithm not found", e);
		} catch (UnsupportedEncodingException e) {
			throw new ProcessingException("unknown string encoding: "
					+ ENCODING, e);
		}

	}

	private boolean areEqual(String pass1, String pass2, String salt)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] bPass = Base64Utility.decode(pass1);
		byte[] bSalt = Base64Utility.decode(salt);
		byte[] bInput = HashUtility.hash(pass2.getBytes(ENCODING), bSalt);

		return Arrays.equals(bInput, bPass);
	}

	@Override
	public boolean isValidUser(String username, String password)
			throws ProcessingException {
		try {
			StringHolder passHolder = new StringHolder();
			StringHolder saltHolder = new StringHolder();
			SQL.selectInto(
					"SELECT pass, salt FROM TABUSERS WHERE UPPER(USERNAME) = UPPER(:username) INTO :pass, :salt",
					new NVPair("username", username), new NVPair("pass",
							passHolder), new NVPair("salt", saltHolder));

			String pass = passHolder.getValue();
			String salt = saltHolder.getValue();
			if (pass == null || salt == null) {
				// user was not found: to prevent time attacks even though check
				// the passwords
				// will always return false
				return areEqual("c29tZXRoaW5n", "dummy", "c29tZXNhbHQ=");
			}
			return areEqual(pass, password, salt);
		} catch (NoSuchAlgorithmException e) {
			throw new ProcessingException("hash algorithm not found", e);
		} catch (UnsupportedEncodingException e) {
			throw new ProcessingException("unknown string encoding: "
					+ ENCODING, e);
		}
	}

	@Override
	public void setUserRoles(Long userId, Long[] roleIds)
			throws ProcessingException {
		SQL.delete("DELETE FROM user_role WHERE u_id = :userId", new NVPair(
				"userId", userId));

		for (Long l : roleIds) {
			SQL.insert(
					"INSERT INTO user_role(u_id, role_id) VALUES (:userId, :roleId)",
					new NVPair("userId", userId), new NVPair("roleId", l));
		}
	}

	@Override
	public void checkUsername(String username) throws VetoException {
		if (StringUtility.length(username) < MIN_USERNAME_LENGTH) {
			throw new VetoException(TEXTS.get("UsernameMinLength", ""
					+ MIN_USERNAME_LENGTH));
		}
		if (username.contains("@")) {
			throw new VetoException(TEXTS.get("UsernameSpecialChars"));
		}
	}

	@Override
	public Long getCurrentUserId() {
		Long id = (Long) ServerJob.getCurrentSession().getData(IStartupService.USER_NR);
		return id;
	}
}
