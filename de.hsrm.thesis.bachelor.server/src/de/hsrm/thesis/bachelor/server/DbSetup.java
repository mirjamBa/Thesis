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

      SQL.insert("CREATE TABLE filetype ("
          + "filetype_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT FILETYPE_PK PRIMARY KEY,"
          + "name        VARCHAR(256) NOT NULL, "
          + "mimetype    VARCHAR(64) NOT NULL, "
          + "language    VARCHAR(32),"
          + "versioning  CHAR NOT NULL)");
      SQL.commit();

      SQL.insert("CREATE TABLE tag ("
          + "tag_id    BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT TAG_PK PRIMARY KEY,"
          + "name      VARCHAR(256) NOT NULL)");
      SQL.commit();

      SQL.insert("CREATE TABLE file ("
          + "file_id   BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT FILE_PK PRIMARY KEY,"
          + "number    BIGINT NOT NULL,"
          + "filetype_id  BIGINT NOT NULL REFERENCES filetype(filetype_id), "
          + "u_id      BIGINT  REFERENCES TABUSERS(u_id))");
      SQL.commit();

      SQL.insert("CREATE TABLE tag_file ("
          + "tag_id    BIGINT NOT NULL REFERENCES tag(tag_id),"
          + "file_id   BIGINT NOT NULL REFERENCES file(file_id),"
          + "PRIMARY KEY(tag_id, file_id))");
      SQL.commit();

      SQL.insert("CREATE TABLE role_file_permission ("
          + "file_id     BIGINT NOT NULL REFERENCES file(file_id),"
          + "permission_id INT NOT NULL,"
          + "PRIMARY KEY(file_id, permission_id))");
      SQL.commit();

      SQL.insert("CREATE TABLE version ("
          + "version_id  BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT VERSIOn_PK PRIMARY KEY,"
          + "file_id     BIGINT NOT NULL REFERENCES file(file_id),"
          + "version     VARCHAR(8) NOT NULL)");
      SQL.commit();

      SQL.insert("CREATE TABLE metadataattribute ("
          + "attribute_id  BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT ATTR_PK PRIMARY KEY,"
          + "name          VARCHAR(256) NOT NULL,"
          + "filetype_id   BIGINT NOT NULL REFERENCES filetype(filetype_id))");
      SQL.commit();

      SQL.insert("CREATE TABLE metadata ("
          + "metadata_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT METADATA_PK PRIMARY KEY,"
          + "version     VARCHAR(8) NOT NULL,"
          + "file_id     BIGINT NOT NULL REFERENCES file(file_id),"
          + "attribute_id  BIGINT NOT NULL REFERENCES metadataattribute(attribute_id),"
          + "value       VARCHAR(512) NOT NULL)");
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
