package de.hsrm.thesis.bachelor.server;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;

import de.hsrm.thesis.bachelor.server.util.UserUtility;
import de.hsrm.thesis.bachelor.shared.services.code.UserRoleCodeType;

/**
 * class that installs the bahbah DB schema
 */
public class DbSetup {
  public static void installDb() throws ProcessingException {
    Set<String> existingTables = getExistingTables();

    if (!existingTables.contains("TABUSERS")) {
      SQL.insert(" CREATE TABLE TABUSERS (" +
          " u_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT USERS_PK PRIMARY KEY, " +
          " username VARCHAR(32) NOT NULL, " +
          " pass VARCHAR(256) NOT NULL, " +
          " salt VARCHAR(64) NOT NULL, " +
          " permission_id INT NOT NULL, " +
          " icon BLOB " +
          ")");
      SQL.commit();

      SQL.insert(" CREATE UNIQUE INDEX IX_USERNAME ON TABUSERS (username) ");
      SQL.commit();

      // create first admin account
      UserUtility.createNewUser("admin", "admin", UserRoleCodeType.AdministratorCode.ID);
      SQL.commit();
    }

  }

  private static Set<String> getExistingTables() throws ProcessingException {
    Object[][] existingTables = SQL.select("SELECT tablename FROM sys.systables");
    HashSet<String> result = new HashSet<String>(existingTables.length);
    for (Object[] row : existingTables) {
      result.add(row[0] + "");
    }
    return result;
  }
}
