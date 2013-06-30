package de.hsrm.mi.storage.database.derby.server.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringHolder;
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
import de.hsrm.thesis.filemanagement.shared.security.UpdateFilePermission;
import de.hsrm.thesis.filemanagement.shared.security.ReadFilePermission;
import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.IMetadataService;
import de.hsrm.thesis.filemanagement.shared.services.IRoleProcessService;
import de.hsrm.thesis.filemanagement.shared.services.IStartupService;
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
		if(!ACCESS.check(new UpdateFilePermission())){
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
		insertRolesForFile(fileId, formData.getRoles().getValue());

		// /////////////METADATA/////////////////

		// individual metadata
		MetadataTableFieldData tableFieldData = formData
				.getFileFormMetadataTable();
		insertUserMetadata(tableFieldData, true, fileId);

		// standardMetadata
		updateDCMIMetadata(formData, fileId);

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
	public FileFormData create(FileFormData formData, ServerFileData fileData)
			throws ProcessingException {
		
		if(!ACCESS.check(new CreateFilePermission())){
			  throw new VetoException(TEXTS.get("VETOCreateFilePermission"));
		  }
		// insert file
		SQL.insert(
				"INSERT INTO file (number, filetype_id, file_path, u_id) VALUES (:number, :filetype_id, :filePath, :u_id)",
				new NVPair("number", fileData.getNumber()), new NVPair(
						"filetype_id", formData.getType().getValue()),
				new NVPair("filePath", fileData.getPath()),
				new NVPair("u_id", (Long) ServerJob.getCurrentSession()
						.getData(IStartupService.USER_NR)));

		// get generated file_id
		LongHolder fileId = new LongHolder();
		SQL.selectInto(
				"SELECT file_id FROM file WHERE number = :number INTO :fileId",
				new NVPair("number", fileData.getNumber()), new NVPair(
						"fileId", fileId));

		insertTagsForFile(fileId.getValue(), formData.getAvailableTagsBox()
				.getValue());
		insertRegeneratedTags(formData, fileId.getValue());

		insertRolesForFile(fileId.getValue(), formData.getRoles().getValue());

		// filemanagement metadata
		// TODO check for Labelnames
		Long entryDateId = SERVICES.getService(
				IUserDefinedAttributesService.class).getAttributeId(
				TEXTS.get("EntryDate"));
		Long filesizeId = SERVICES.getService(
				IUserDefinedAttributesService.class).getAttributeId(
				TEXTS.get("Filesize"));
		Long fileExtensionId = SERVICES.getService(
				IUserDefinedAttributesService.class).getAttributeId(
				TEXTS.get("FileExtension"));
		SERVICES.getService(IMetadataService.class).addMetadata(entryDateId,
				fileId.getValue(),
				m_formatter.format(formData.getCreationDate().getValue()));
		SERVICES.getService(IMetadataService.class).addMetadata(filesizeId,
				fileId.getValue(), formData.getFilesize().getValue());
		SERVICES.getService(IMetadataService.class).addMetadata(
				fileExtensionId, fileId.getValue(),
				formData.getFileExtension().getValue());

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
		if (!ACCESS.check(new ReadFilePermission())) {
			throw new VetoException(TEXTS.get("VETOReadFilePermission"));
		}
		Long user_id = (Long) ServerJob.getCurrentSession().getData(
				IStartupService.USER_NR);

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder
				.append("SELECT DISTINCT"
						+ "            F.FILE_ID, "
						+ "            F.FILETYPE_ID, "
						+ "            F.NUMBER, "
						+ "            F.U_ID, "
						+ "            F.FILE_PATH "
						+ "     FROM   METADATA M,"
						+ "            FILE F "
						+ "     LEFT OUTER JOIN TAG_FILE T " // show files
																// without tags
						+ "     ON F.FILE_ID = T.FILE_ID "
						+ "     WHERE  1 = 1 "
						+ "     AND    F.FILE_ID = M.FILE_ID "
						+ "     AND    (F.U_ID = :userId " // visible for the
															// typist of the
															// file
						+ "     OR     F.FILE_ID IN "
						+ "             (SELECT RF.FILE_ID FROM ROLE_FILE_PERMISSION RF WHERE RF.ROLE_ID IN " // visible
																												// for
																												// users
																												// who
																												// are
																												// a
																												// member
																												// of
																												// those
																												// roles,
																												// the
																												// files
																												// are
																												// approved
																												// for
						+ "                     (SELECT R.ROLE_ID FROM USER_ROLE R WHERE R.U_ID = :userId) "
						+ "             ) " + "             ) ");

		// general search
		if (searchFormData.getGeneralSearch().getValue() != null) {
			stringBuilder
					.append(" AND  UPPER(M.VALUE) LIKE UPPER('%' || :generalSearch || '%')");
		}
		// standard search
		if (searchFormData.getFileType().getValue() != null) {
			stringBuilder.append(" AND F.FILETYPE_ID = :fileType ");
		}
		if (searchFormData.getTypist().getValue() != null) {
			stringBuilder.append(" AND F.U_ID = :typist ");
		}
		if (searchFormData.getFileNrFrom().getValue() != null) {
			stringBuilder.append(" AND F.NUMBER >= :fileNrFrom ");
		}
		if (searchFormData.getFileNrTo().getValue() != null) {
			stringBuilder.append(" AND F.NUMBER <= :fileNrTo ");
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
				user_id), bindings, searchFormData);
	}

	@Override
	public ServerFileData saveFile(File file) throws ProcessingException {
		
		if(!ACCESS.check(new CreateFilePermission())){
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
		StringHolder path = new StringHolder();
		SQL.selectInto(
				"SELECT file_path FROM file WHERE file_id = :fileNr INTO :path",
				new NVPair("fileNr", fileNr), new NVPair("path", path));

		return new File(path.getValue());

	}

	@Override
	public void updateRoleFilePermission(Long fildId, Long[] roleIds)
			throws ProcessingException {
		
		if(!ACCESS.check(new FreeFilePermission())){
			  throw new VetoException(TEXTS.get("VETOFreeFilePermission"));
		  }

		SQL.delete("DELETE FROM role_file_permission WHERE file_id= :fileId",
				new NVPair("fileId", fildId));

		for (Long roleId : roleIds) {
			SQL.insert(
					"INSERT INTO role_file_permission (file_id, role_id) VALUES (:fileId, :roleId)",
					new NVPair("fileId", fildId), new NVPair("roleId", roleId));
		}
	}

	@Override
	public boolean deleteFile(Long fileId) throws ProcessingException {
		if(!ACCESS.check(new DeleteFilePermission())){
			  throw new VetoException(TEXTS.get("VETODeleteFilePermission"));
		  }
		// remove tag-file-connection
		SQL.delete("DELETE FROM tag_file WHERE file_id = :fileId", new NVPair(
				"fileId", fileId));
		// remove unused permissions for file
		SQL.delete("DELETE FROM role_file_permission WHERE file_id = :fileId",
				new NVPair("fileId", fileId));

		// save filepath from database
		StringHolder path = new StringHolder();
		SQL.selectInto(
				"SELECT file_path FROM file WHERE file_id = :fileId INTO :path",
				new NVPair("fileId", fileId), new NVPair("path", path));

		// remove filedata in database
		SQL.delete("DELETE FROM metadata WHERE file_id = :fileId", new NVPair(
				"fileId", fileId));
		SQL.delete("DELETE FROM version WHERE file_id = :fileId", new NVPair(
				"fileId", fileId));
		SQL.delete("DELETE FROM file WHERE file_id = :fileId", new NVPair(
				"fileId", fileId));

		// remove file from server
		System.out.println(path.getValue());
		File file = new File(path.getValue());
		return file.delete();
	}

}
