package de.hsrm.mi.storage.database.derby.server.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractFormFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.shared.formdata.FileFormData;
import de.hsrm.thesis.filemanagement.shared.formdata.FileSearchFormData;
import de.hsrm.thesis.filemanagement.shared.formdata.MetadataTableFieldData;
import de.hsrm.thesis.filemanagement.shared.nonFormdataBeans.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.security.CreateFilePermission;
import de.hsrm.thesis.filemanagement.shared.security.DeleteFilePermission;
import de.hsrm.thesis.filemanagement.shared.security.FreeFilePermission;
import de.hsrm.thesis.filemanagement.shared.security.ReadFilePermission;
import de.hsrm.thesis.filemanagement.shared.security.UpdateFilePermission;
import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.IFolderService;
import de.hsrm.thesis.filemanagement.shared.services.IMetadataService;
import de.hsrm.thesis.filemanagement.shared.services.IRoleProcessService;
import de.hsrm.thesis.filemanagement.shared.services.IStartupService;
import de.hsrm.thesis.filemanagement.shared.services.IStorageService;
import de.hsrm.thesis.filemanagement.shared.services.ITagService;
import de.hsrm.thesis.filemanagement.shared.services.IUserDefinedAttributesService;
import de.hsrm.thesis.filemanagement.shared.services.code.DatatypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.code.DublinCoreMetadataElementSetCodeType;
import de.hsrm.thesis.filemanagement.shared.services.code.ICategorizable;

public class FileService extends AbstractService implements IFileService {
	private SimpleDateFormat m_formatter = new SimpleDateFormat(
			TEXTS.get("SimpleDateFormat"));
	private String filePath;

	public void setFilePath(String path) {
		this.filePath = path;
	}

	public String getFilePath() {
		return filePath;
	}

	@Override
	public FileFormData update(FileFormData formData)
			throws ProcessingException {
		if (!ACCESS.check(new UpdateFilePermission())) {
			throw new VetoException(TEXTS.get("VETOModifyFilePermission"));
		}
		Long fileId = formData.getFileNr();

		// //////////////TAG//////////////////////
		// delete old tag-file-connection
		SERVICES.getService(ITagService.class).deleteTagsForFile(fileId);
		// insert modified tag-file-connection
		insertTagsForFile(fileId, formData.getAvailableTagsBox().getValue());
		// insert new tags
		insertRegeneratedTags(formData, fileId);

		// ////////////////ROLE//////////////////
		// delete old role-file-connection
		SERVICES.getService(IRoleProcessService.class).deleteRolesForFile(
				fileId);
		// insert modified role-file-connection
		insertRolesForFile(fileId, formData.getFileFormRoles().getValue());

		// /////////////METADATA/////////////////

		// individual metadata
		MetadataTableFieldData tableFieldData = formData
				.getFileFormMetadataTable();
		insertUserMetadata(tableFieldData, true, fileId);

		// standardMetadata
		updateDCMIMetadata(formData, fileId);
		
		SQL.update("UPDATE file_folder SET name = :name WHERE file_folder_id = :id", 
				new NVPair("name", formData.getTitle().getValue()), 
						new NVPair("id", fileId));

		return formData;
	}
	

	private void insertUserMetadata(MetadataTableFieldData tableData,
			boolean update, Long fileId) throws ProcessingException {
		int rows = tableData.getRowCount();
		for (int i = 0; i < rows; i++) {
			Long attributeId = tableData.getAttributID(i);
			String metadata = tableData.getValue(i);
			if (update) {
				SERVICES.getService(IMetadataService.class)
						.updateOrInsertMetadata(attributeId, fileId, metadata);
			} else {
				SERVICES.getService(IMetadataService.class).addMetadata(
						attributeId, fileId, metadata);
			}
		}
	}

	private void insertDCMIMetadata(FileFormData formData, Long fileId)
			throws ProcessingException {
		// dcmi metadata
		Map<Long, String> standardMetadata = extractDublinCoreMetadata(formData);
		for (Long attributeId : standardMetadata.keySet()) {
			System.out.println("ATTRIBUTE_ID: " + attributeId + " ------ "
					+ standardMetadata.get(attributeId));
			SERVICES.getService(IMetadataService.class).addMetadata(
					attributeId, fileId, standardMetadata.get(attributeId));
		}
	}

	private void updateDCMIMetadata(FileFormData formData, Long fileId)
			throws ProcessingException {
		// dcmi metadata
		Map<Long, String> standardMetadata = extractDublinCoreMetadata(formData);
		for (Long attributeId : standardMetadata.keySet()) {
			SERVICES.getService(IMetadataService.class).updateOrInsertMetadata(
					attributeId, fileId, standardMetadata.get(attributeId));
		}
	}

	@Override
	public FileFormData create(FileFormData formData, ServerFileData fileData,
			Long parentFolderId) throws ProcessingException {

		if (!ACCESS.check(new CreateFilePermission())) {
			throw new VetoException(TEXTS.get("VETOCreateFilePermission"));
		}
		SQL.insert(
				"INSERT INTO file_folder (name, u_id, parent_folder, is_folder) VALUES (:name, :uId, :parent, :isFolder)",
				new NVPair("name", formData.getTitle().getValue()),
				new NVPair("uId", (Long) ServerJob.getCurrentSession().getData(
						IStartupService.USER_NR)), new NVPair("parent",
						parentFolderId), new NVPair("isFolder", false));

		// get generated file_id
		LongHolder fileId = new LongHolder();
		SQL.selectInto(
				"SELECT max(file_folder_id) FROM file_folder INTO :fileId",
				new NVPair("fileId", fileId));

		insertTagsForFile(fileId.getValue(), formData.getAvailableTagsBox()
				.getValue());
		insertRegeneratedTags(formData, fileId.getValue());

		insertRolesForFile(fileId.getValue(), formData.getFileFormRoles().getValue());

		// filemanagement metadata
		Long entryDateId = SERVICES.getService(
				IUserDefinedAttributesService.class).getAttributeId(
						IStorageService.META_ENTRYDATE);
		Long filesizeId = SERVICES.getService(
				IUserDefinedAttributesService.class).getAttributeId(
						IStorageService.META_FILESIZE);
		Long fileExtensionId = SERVICES.getService(
				IUserDefinedAttributesService.class).getAttributeId(
						IStorageService.META_FILEEXTENSION);
		Long numberId = SERVICES
				.getService(IUserDefinedAttributesService.class)
				.getAttributeId(IStorageService.META_NUMBER);
		Long filepathId = SERVICES.getService(
				IUserDefinedAttributesService.class).getAttributeId(
						IStorageService.META_FILEPATH);

		SERVICES.getService(IMetadataService.class).addMetadata(entryDateId,
				fileId.getValue(),
				m_formatter.format(formData.getCreationDate().getValue()));
		SERVICES.getService(IMetadataService.class).addMetadata(filesizeId,
				fileId.getValue(), formData.getFilesize().getValue());
		SERVICES.getService(IMetadataService.class).addMetadata(
				fileExtensionId, fileId.getValue(),
				formData.getFileExtension().getValue());

		SERVICES.getService(IMetadataService.class).addMetadata(numberId,
				fileId.getValue(), fileData.getNumber().toString());
		SERVICES.getService(IMetadataService.class).addMetadata(filepathId,
				fileId.getValue(), fileData.getPath());

		// dcmi metadata
		insertDCMIMetadata(formData, fileId.getValue());

		// insert individual metadata
		MetadataTableFieldData tableData = formData.getFileFormMetadataTable();
		insertUserMetadata(tableData, false, fileId.getValue());

		return formData;
	}

	private void insertRegeneratedTags(FileFormData formData, Long fileId)
			throws ProcessingException {
		// check for new Tags
		String newTags = formData.getNewTag().getValue();
		if (newTags != null) {
			String[] tags = SERVICES.getService(ITagService.class)
					.filterTagnames(newTags);
			SERVICES.getService(ITagService.class).create(tags);

			// connect new tags to new file
			for (String s : tags) {
				Long tagId = SERVICES.getService(ITagService.class).getTagId(s);
				createTagFileConnection(fileId, tagId);
			}
		}
	}

	private void insertTagsForFile(Long fileId, Long[] tagIds)
			throws ProcessingException {
		// check for available tags
		if (SERVICES.getService(ITagService.class).tagsExisting()) {
			// insert tag-file connection
			if (tagIds != null) {
				for (Long tagId : tagIds) {
					createTagFileConnection(fileId, tagId);
				}
			}
		}
	}

	private void insertRolesForFile(Long fileId, Long[] roleIds)
			throws ProcessingException {
		// insert role-file connecntion
		if (roleIds != null) {
			for (Long roleId : roleIds) {
				SQL.insert(
						"INSERT INTO role_file_permission (file_id, role_id) VALUES (:fileId, :roleId)",
						new NVPair("fileId", fileId), new NVPair("roleId",
								roleId));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Map<Long, String> extractDublinCoreMetadata(FileFormData formData)
			throws ProcessingException {
		// get DublinCore Metadata Codes
		ICode<Long>[] codes = CODES.getCodeType(
				DublinCoreMetadataElementSetCodeType.class).getCodes();
		// get all fields
		Map<String, AbstractFormFieldData> map = formData.getAllFieldsRec()
				.get(0);

		Map<Long, String> metadata = new HashMap<Long, String>();

		for (ICode<Long> attributeCode : codes) {
			String[] className = attributeCode.getClass().getCanonicalName()
					.split("\\.");
			String codeName = className[(className.length - 1)].replace("Code",
					"");

			if (map.containsKey(codeName)) {
				// get attribute id for code
				Long attrId = getAttributeId(attributeCode);
				String value = null;

				// extract Value from fieldData
				Long codeDatatype = ((ICategorizable) attributeCode)
						.getCategory();
				if (codeDatatype.equals(DatatypeCodeType.StringCode.ID)) {
					value = ((AbstractValueFieldData<String>) map.get(codeName))
							.getValue();
				} else if (codeDatatype.equals(DatatypeCodeType.DateCode.ID)) {
					Date date = ((AbstractValueFieldData<Date>) map
							.get(codeName)).getValue();
					if (date != null) {
						value = m_formatter.format(date);
					}
				} else if (codeDatatype.equals(DatatypeCodeType.LongCode.ID)) {
					Long l = ((AbstractValueFieldData<Long>) map.get(codeName))
							.getValue();
					if (l != null) {
						value = l.toString();
					}
				} else if (codeDatatype.equals(DatatypeCodeType.DoubleCode.ID)) {
					Double d = ((AbstractValueFieldData<Double>) map
							.get(codeName)).getValue();
					if (d != null) {
						value = d.toString();
					}
				}

				if (value != null) {
					metadata.put(attrId, value);
				}
			}
		}

		return metadata;
	}

	private Long getAttributeId(ICode<Long> code) throws ProcessingException {
		String text = code.getText();
		return SERVICES.getService(IUserDefinedAttributesService.class)
				.getAttributeId(text);
	}

	private void createTagFileConnection(Long fileId, Long tagId)
			throws ProcessingException {
		SQL.insert(
				"INSERT INTO tag_file (tag_id, file_id) VALUES (:tagId, :fileId)",
				new NVPair("tagId", tagId), new NVPair("fileId", fileId));
	}

	@Override
	public Object[][] getFiles(FileSearchFormData searchFormData)
			throws ProcessingException {
		return getFileTableData(searchFormData, null, true);
	}

	private Object[][] getFileTableData(FileSearchFormData searchFormData,
			Long folderId, boolean allFiles) throws ProcessingException {
		if (!ACCESS.check(new ReadFilePermission())) {
			throw new VetoException(TEXTS.get("VETOReadFilePermission"));
		}
		Long user_id = (Long) ServerJob.getCurrentSession().getData(
				IStartupService.USER_NR);

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder
				.append("SELECT DISTINCT"
						+ "            F.FILE_FOLDER_ID, "
						+ "			   F.NAME, "
						+ "            F.U_ID, "
						+ "            F.IS_FOLDER "
						+ "		FROM   FILE_FOLDER F "
						+ "		LEFT OUTER JOIN METADATA M "
						+ "		ON F.FILE_FOLDER_ID = M.FILE_ID "
						// + "     FROM   METADATA M,"
						// + "            FILE_FOLDER F "
						// show files without tags
						+ "     LEFT OUTER JOIN TAG_FILE T "
						+ "     ON F.FILE_FOLDER_ID = T.FILE_ID "
						+ "     WHERE  1 = 1 "
						// + "     AND    F.FILE_FOLDER_ID = M.FILE_ID "
						// visible for the typist of the file
						+ "     AND    (F.U_ID = :userId "
						+ "     OR     F.FILE_FOLDER_ID IN "
						// visible for users who are a member of those roles,
						// the files are approved for
						+ "             (SELECT RF.FILE_ID FROM ROLE_FILE_PERMISSION RF WHERE RF.ROLE_ID IN "
						+ "                     (SELECT R.ROLE_ID FROM USER_ROLE R WHERE R.U_ID = :userId) "
						+ "             ) " + "             ) ");

		if (!allFiles && searchFormData.getFolderSearch().getValue()) {
			if (folderId == null) {
				stringBuilder.append(" AND parent_folder IS NULL ");
			} else {
				stringBuilder.append(" AND parent_folder = :folderId ");
			}
		}

		// general search
		if (searchFormData.getGeneralSearch().getValue() != null) {
			stringBuilder
					.append(" AND  UPPER(M.VALUE) LIKE UPPER('%' || :generalSearch || '%')");
		}
		if (searchFormData.getTypist().getValue() != null) {
			stringBuilder.append(" AND F.U_ID = :typist ");
		}
		// detailed search
		int rows = searchFormData.getFileSearchMetadataTable().getRowCount();
		Map<String, Object> bindings = new HashMap<String, Object>();
		for (int i = 0; i < rows; i++) {
			String value = (String) searchFormData.getFileSearchMetadataTable()
					.getValueAt(i, 2);
			if (value != null) {
				Long attrId = (Long) searchFormData
						.getFileSearchMetadataTable().getValueAt(i, 0);
				stringBuilder
						.append(" AND UPPER(M.VALUE) LIKE UPPER('%' || :value"
								+ i
								+ " || '%') "
								+ "                AND M.ATTRIBUTE_ID = :attrId"
								+ i + " ");
				bindings.put("value" + i, value);
				bindings.put("attrId" + i, attrId);
			}
		}
		// tag search
		if (searchFormData.getTag().getValue() != null) {
			stringBuilder.append(" AND T.TAG_ID = :tag ");
		}
		return SQL.select(stringBuilder.toString(), new NVPair("userId",
				user_id), new NVPair("folderId", folderId), bindings,
				searchFormData);
	}

	@Override
	public Object[][] getFolderFiles(FileSearchFormData searchFormData,
			Long folderId) throws ProcessingException {
		return getFileTableData(searchFormData, folderId, false);
	}

	@Override
	public ServerFileData saveFile(File file) throws ProcessingException {

		if (!ACCESS.check(new CreateFilePermission())) {
			throw new VetoException(TEXTS.get("VETOCreateFilePermission"));
		}

		byte[] content;
		File serverFile;

		String fileEnding = file.getName().split("\\.")[file.getName().split(
				"\\.").length - 1];
		String currentDate = generateCurrentDate(TEXTS.get("StorageDirectory"));
		String[] parts = currentDate.split(":");

		String fileServerPath;
		Long nextFileNr = getNextFileNumber();
		String newDirectory = getFilePath() + parts[0];

		try {
			File dir = new File(newDirectory);
			dir.mkdirs();

			// create new file on server
			fileServerPath = newDirectory + "/" + parts[1] + "_" + nextFileNr
					+ "." + fileEnding;
			serverFile = new File(fileServerPath);
			serverFile.createNewFile();

			// write content to new file
			content = IOUtility.getContent(new FileInputStream(file));
			IOUtility.writeContent(new FileOutputStream(serverFile), content,
					true);

		} catch (FileNotFoundException e1) {
			throw new ProcessingException(e1.getMessage());
		} catch (IOException e) {
			throw new ProcessingException(e.getMessage());
		}
		Icon icon = FileSystemView.getFileSystemView()
				.getSystemIcon(serverFile);
		ServerFileData fileData = new ServerFileData(
				serverFile.getAbsolutePath(), nextFileNr, serverFile.length(),
				icon);
		fileData.setOldName(file.getName().split("\\.")[0]);
		try {
			fileData.setLastModified(m_formatter.parse(m_formatter.format(file
					.lastModified())));
		} catch (ParseException e) {
			// no date set
		}

		return fileData;
	}

	private String generateCurrentDate(String format) {
		Date now = new Date();
		String datePath = new SimpleDateFormat(format).format(now);

		return datePath;
	}

	private Long getNextFileNumber() throws ProcessingException {
		Object[][] seqNr = SQL.select("VALUES NEXT VALUE FOR file_number");
		Long fileNumber = (Long) seqNr[0][0];
		return fileNumber;
	}

	@Override
	public File getServerFile(Long fileNr) throws ProcessingException {
		String pathAttr = (String) SERVICES.getService(IMetadataService.class)
				.getMetadataValue(TEXTS.get("Filepath"), fileNr);

		return new File(pathAttr);

	}

	@Override
	public void freeFile(Long fileId, Long[] roleIds)
			throws ProcessingException {

		if (!ACCESS.check(new FreeFilePermission())) {
			throw new VetoException(TEXTS.get("VETOFreeFilePermission"));
		}

		SQL.delete("DELETE FROM role_file_permission WHERE file_id= :fileId",
				new NVPair("fileId", fileId));

		if (roleIds != null) {
			// insert selected roles
			for (Long roleId : roleIds) {
				SQL.insert(
						"INSERT INTO role_file_permission (file_id, role_id) VALUES (:fileId, :roleId)",
						new NVPair("fileId", fileId), new NVPair("roleId",
								roleId));
			}
		}
	}

	@Override
	public void updateRoleFileAndFolderPermission(Long fileId, Long[] roleIds)
			throws ProcessingException {
		// free file
		freeFile(fileId, roleIds);

		// get all parent folders and add roles
		Long id = new Long(fileId);
		List<Long> parentFolder = new ArrayList<Long>();

		while (id != null) {
			Long parent = SERVICES.getService(IFolderService.class)
					.getParentFolder(id);
			id = parent;
			if (id != null) {
				parentFolder.add(parent);
			}
		}

		for (Long parentFolderId : parentFolder) {
			SERVICES.getService(IFolderService.class).addFileFolderFreeing(
					parentFolderId, roleIds);
		}

	}

	@Override
	public void updateFileFreeing(Long fileId, Long[] roleIds)
			throws ProcessingException {

	}

	@Override
	public boolean deleteFile(Long fileId) throws ProcessingException {
		if (!ACCESS.check(new DeleteFilePermission())) {
			throw new VetoException(TEXTS.get("VETODeleteFilePermission"));
		}
		// remove tag-file-connection
		SQL.delete("DELETE FROM tag_file WHERE file_id = :fileId", new NVPair(
				"fileId", fileId));
		// remove unused permissions for file
		SQL.delete("DELETE FROM role_file_permission WHERE file_id = :fileId",
				new NVPair("fileId", fileId));

		// save filepath from database
		String path = (String) SERVICES.getService(IMetadataService.class)
				.getMetadataValue(TEXTS.get("Filepath"), fileId);
		// StringHolder path = new StringHolder();
		// SQL.selectInto(
		// "SELECT file_path FROM file WHERE file_id = :fileId INTO :path",
		// new NVPair("fileId", fileId), new NVPair("path", path));

		// remove filedata in database
		SQL.delete("DELETE FROM metadata WHERE file_id = :fileId", new NVPair(
				"fileId", fileId));
		SQL.delete("DELETE FROM version WHERE file_id = :fileId", new NVPair(
				"fileId", fileId));
		SQL.delete("DELETE FROM file_folder WHERE file_folder_id = :fileId",
				new NVPair("fileId", fileId));

		// remove file from server
		System.out.println(path);
		File file = new File(path);
		return file.delete();
	}

}
