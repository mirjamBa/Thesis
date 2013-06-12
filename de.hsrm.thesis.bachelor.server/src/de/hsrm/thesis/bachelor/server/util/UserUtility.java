/*******************************************************************************
 * Copyright (c) 2010 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package de.hsrm.thesis.bachelor.server.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.eclipse.scout.commons.Base64Utility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringHolder;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;

import de.hsrm.thesis.bachelor.shared.services.code.UserRoleCodeType.AdministratorCode;
import de.hsrm.thesis.bachelor.shared.services.code.UserRoleCodeType.UserCode;
import de.hsrm.thesis.bachelor.shared.util.SharedUserUtility;

public class UserUtility extends SharedUserUtility {

  private static final String ENCODING = "UTF-8";
  public static final String USER_ROLE_ADMIN = "Administrator";
  public static final String USER_ROLE_USER = "User";

  public static boolean createNewUser(String username, String password) throws ProcessingException {
    return createNewUser(username, password, new Long[0]);
  }

  public static boolean isAdmin(Long[] roles) throws ProcessingException {
    Long adminId = getAdminRoleId();
    for (Long l : roles) {
      if (l.equals(adminId)) {
        return true;
      }
    }
    return false;
  }

  public static boolean createNewUser(String username, String password, Long[] roleIds) throws ProcessingException {
    try {
      checkUsername(username);
      checkPassword(password);

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

  /**
   * @author BSI
   * @param u_Id
   * @param newPassword
   * @throws ProcessingException
   */
  public static void resetPassword(Long u_Id, String newPassword) throws ProcessingException {
    try {
      checkPassword(newPassword);

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

  /**
   * @author BSI
   * @param username
   * @param password
   * @return
   * @throws ProcessingException
   */
  public static boolean isValidUser(String username, String password) throws ProcessingException {
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

  /**
   * @author BSI
   * @param pass1
   * @param pass2
   * @param salt
   * @return
   * @throws UnsupportedEncodingException
   * @throws NoSuchAlgorithmException
   */
  private static boolean areEqual(String pass1, String pass2, String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    byte[] bPass = Base64Utility.decode(pass1);
    byte[] bSalt = Base64Utility.decode(salt);
    byte[] bInput = HashUtility.hash(pass2.getBytes(ENCODING), bSalt);

    return Arrays.equals(bInput, bPass);
  }

  public static Long getRoleId(String name) throws ProcessingException {
    LongHolder idHolder = new LongHolder();
    SQL.selectInto("SELECT role_id FROM role WHERE UPPER(name) = UPPER(:rolename) INTO :id",
        new NVPair("rolename", name),
        new NVPair("id", idHolder));

    return idHolder.getValue();
  }

  public static Long getAdminRoleId() throws ProcessingException {
    return getRoleId(USER_ROLE_ADMIN);
  }

  public static Long getUserRoleId() throws ProcessingException {
    return getRoleId(USER_ROLE_USER);
  }

  public static Long getUserId(String username) throws ProcessingException {
    LongHolder userId = new LongHolder();
    SQL.selectInto("SELECT u_id FROM tabusers WHERE username = :username INTO :userId",
        new NVPair("username", username),
        new NVPair("userId", userId));
    return userId.getValue();
  }

  public static void setUserRoles(Long userId, Long[] roleIds) throws ProcessingException {
    SQL.delete("DELETE FROM user_role WHERE u_id = :userId", new NVPair("userId", userId));

    for (Long l : roleIds) {
      SQL.insert("INSERT INTO user_role(u_id, role_id) VALUES (:userId, :roleId)",
          new NVPair("userId", userId),
          new NVPair("roleId", l));
    }
  }
}
