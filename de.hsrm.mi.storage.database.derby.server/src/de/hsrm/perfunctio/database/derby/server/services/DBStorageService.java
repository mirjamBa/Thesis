package de.hsrm.perfunctio.database.derby.server.services;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.services.IRoleProcessService;
import de.hsrm.perfunctio.core.shared.services.IStorageService;
import de.hsrm.perfunctio.core.shared.services.IUserProcessService;
import de.hsrm.perfunctio.core.shared.services.code.DatatypeCodeType;
import de.hsrm.perfunctio.core.shared.services.code.DublinCoreMetadataElementSetCodeType;
import de.hsrm.perfunctio.core.shared.services.code.FileTypeCodeType;
import de.hsrm.perfunctio.core.shared.services.code.ICategorizable;

/**
 * class that installs the bachelor DB schema
 */
public class DBStorageService extends AbstractService
		implements
			IStorageService {
	private IScoutLogger logger = ScoutLogManager.getLogger(DBStorageService.class);

	@Override
	public void installStorage() throws ProcessingException {
		Set<String> existingTables = getExistingTables();

		if (!existingTables.contains("TABUSERS")) {
			logger.info("install database");
			SQL.insert(" CREATE TABLE TABUSERS ("
					+ " u_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT USERS_PK PRIMARY KEY, "
					+ " username VARCHAR(32) NOT NULL, "
					+ " pass VARCHAR(256) NOT NULL, "
					+ " salt VARCHAR(64) NOT NULL ) ");
			SQL.commit();

			SQL.insert(" CREATE UNIQUE INDEX IX_USERNAME ON TABUSERS (username) ");
			SQL.commit();

			SQL.insert("CREATE TABLE filetype_version_control ("
					+ "filetype_id  BIGINT NOT NULL PRIMARY KEY, "
					+ "version_control BOOLEAN NOT NULL)");
			SQL.commit();

			SQL.insert("CREATE TABLE tag ("
					+ "tag_id    BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT TAG_PK PRIMARY KEY,"
					+ "name      VARCHAR(256) NOT NULL)");
			SQL.commit();
			
			SQL.insert("CREATE TABLE file_folder ( "
					+ "file_folder_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT FILE_FOLDER_PK PRIMARY KEY, "
					+ "name 		  VARCHAR(256) NOT NULL, "
					+ "u_id			  BIGINT NOT NULL REFERENCES TABUSERS(u_id), "
					+ "parent_folder  BIGINT, "
					+ "is_folder	  BOOLEAN NOT NULL )");
			SQL.commit();

			SQL.insert("CREATE TABLE tag_file ("
					+ "tag_id    BIGINT NOT NULL REFERENCES tag(tag_id),"
					+ "file_id   BIGINT NOT NULL REFERENCES file_folder(file_folder_id),"
					+ "PRIMARY KEY(tag_id, file_id))");
			SQL.commit();

			SQL.insert("CREATE TABLE metadata_attribute ("
					+ "attribute_id  BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT ATTR_PK PRIMARY KEY,"
					+ "name          VARCHAR(256) NOT NULL,"
					+ "datatype      BIGINT NOT NULL,"
					+ "filetype_id   BIGINT,"
					+ "show_in_table BOOLEAN NOT NULL DEFAULT FALSE)");
			SQL.commit();

			SQL.insert("CREATE TABLE metadata ("
					+ "metadata_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT METADATA_PK PRIMARY KEY,"
					+ "file_id     BIGINT NOT NULL REFERENCES file_folder(file_folder_id),"
					+ "attribute_id  BIGINT NOT NULL REFERENCES metadata_attribute(attribute_id),"
					+ "value       VARCHAR(512))");
			SQL.commit();

			SQL.insert("CREATE TABLE fileformat ("
					+ "fileformat_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT FILEFORMAT_PK PRIMARY KEY,"
					+ "format        VARCHAR(8) NOT NULL,"
					+ "filetype_id   BIGINT NOT NULL)");
			SQL.commit();

			SQL.insert("CREATE TABLE role ("
					+ "role_id       BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT ROLE_PK PRIMARY KEY, "
					+ "name          VARCHAR(128) NOT NULL,"
					+ "user_creator_id  BIGINT REFERENCES tabusers(u_id))");
			SQL.commit();

			SQL.insert("CREATE TABLE role_file_permission ( "
					+ "free_id	   BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT FREE_PK PRIMARY KEY, "
					+ "file_id     BIGINT NOT NULL REFERENCES file_folder(file_folder_id),"
					+ "role_id     BIGINT NOT NULL REFERENCES role(role_id), "
					+ "permission_name	VARCHAR(256))");
			SQL.commit();

			SQL.insert("CREATE TABLE user_role ("
					+ "u_id          BIGINT NOT NULL REFERENCES tabusers(u_id),"
					+ "role_id       BIGINT NOT NULL REFERENCES role(role_id),"
					+ "PRIMARY KEY (u_id, role_id))");
			SQL.commit();

			SQL.insert("CREATE SEQUENCE file_number AS BIGINT START WITH 1000");
			SQL.commit();
			
			SQL.insert("CREATE TABLE role_permission ( "
					+ "role_id		BIGINT NOT NULL REFERENCES role(role_id), "
					+ "permission_name	VARCHAR(256))");
			SQL.commit();
			
			//create default roles
			SQL.insert("INSERT INTO role (name, user_creator_id) VALUES ('"
					+ IRoleProcessService.USER_ROLE_ADMIN + "', null)");
			SQL.insert("INSERT INTO role (name, user_creator_id) VALUES ('"
					+ IRoleProcessService.USER_ROLE_USER + "', null)");

			// create first admin account
			Long[] roles = new Long[]{SERVICES.getService(
					IRoleProcessService.class).getAdminRoleId()};
			SERVICES.getService(IUserProcessService.class).createNewUser(
					"admin", "admin", roles);

			SQL.commit();

			//insert dublin core filetypes
			@SuppressWarnings("unchecked")
			ICode<Long>[] codes = CODES.getCodeType(FileTypeCodeType.class)
					.getCodes();
			for (ICode<Long> code : codes) {
				SQL.insert(
						"INSERT INTO filetype_version_control (filetype_id, version_control) VALUES (:filetypeId, 'false')",
						new NVPair("filetypeId", code.getId()));
				SQL.commit();
			}

			//inser dublin core metadata element set
			@SuppressWarnings("unchecked")
			ICode<Long>[] meta = CODES.getCodeType(
					DublinCoreMetadataElementSetCodeType.class).getCodes();
			for (ICode<Long> code : meta) {
				SQL.insert(
						"INSERT INTO metadata_attribute (name, datatype) VALUES (:name, :datatype)",
						new NVPair("name", code.getText()),
						new NVPair("datatype", ((ICategorizable) code)
								.getCategory()));
				SQL.commit();
			}

			//insert some extra metadata
			SQL.insert(
					"INSERT INTO metadata_attribute (name, datatype) VALUES (:name, :datatype)",
					new NVPair("name", IStorageService.META_ENTRYDATE), new NVPair(
							"datatype", DatatypeCodeType.DateCode.ID));

			SQL.insert(
					"INSERT INTO metadata_attribute (name, datatype) VALUES (:name, :datatype)",
					new NVPair("name", IStorageService.META_FILESIZE), new NVPair(
							"datatype", DatatypeCodeType.StringCode.ID));

			SQL.insert(
					"INSERT INTO metadata_attribute (name, datatype) VALUES (:name, :datatype)",
					new NVPair("name", IStorageService.META_FILEEXTENSION), new NVPair(
							"datatype", DatatypeCodeType.StringCode.ID));
			
			SQL.insert(
					"INSERT INTO metadata_attribute (name, datatype) VALUES (:name, :datatype)",
					new NVPair("name", IStorageService.META_NUMBER), new NVPair(
							"datatype", DatatypeCodeType.LongCode.ID));
			
			SQL.insert(
					"INSERT INTO metadata_attribute (name, datatype) VALUES (:name, :datatype)",
					new NVPair("name", IStorageService.META_FILEPATH), new NVPair(
							"datatype", DatatypeCodeType.StringCode.ID));
			
			

		}

	}

	private Set<String> getExistingTables() throws ProcessingException {
		logger.info("check existing tables");
		Object[][] existingTables = SQL
				.select("SELECT tablename FROM sys.systables");
		HashSet<String> result = new HashSet<String>(existingTables.length);
		for (Object[] row : existingTables) {
			result.add(row[0] + "");
		}
		return result;
	}

}
