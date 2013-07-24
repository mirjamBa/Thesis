package de.hsrm.perfunctio.database.derby.server.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.LongArrayHolder;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringHolder;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;
import org.osgi.framework.ServiceRegistration;

import de.hsrm.perfunctio.core.shared.services.IFileService;
import de.hsrm.perfunctio.core.shared.services.IFolderService;
import de.hsrm.perfunctio.core.shared.services.IStartupService;
import de.hsrm.perfunctio.core.shared.services.formdata.FoldersFormData;

public class FolderService extends AbstractService implements IFolderService {

	private Map<Long, String> folderNames;

	@SuppressWarnings("rawtypes")
	@Override
	public void initializeService(ServiceRegistration registration) {
		super.initializeService(registration);
		folderNames = Collections.synchronizedMap(new HashMap<Long, String>());
	}

	@Override
	public FoldersFormData createFolder(FoldersFormData formData,
			Long parentFolderId) throws ProcessingException {
		SQL.insert(
				"INSERT INTO file_folder "
						+ "		(name, u_id, parent_folder, is_folder) "
						+ "		VALUES (:name, :uId, :parent, :isFolder) ",
				new NVPair("name", formData.getName().getValue()),
				new NVPair("uId", ServerJob.getCurrentSession().getData(
						IStartupService.USER_NR)), new NVPair("parent",
						parentFolderId), new NVPair("isFolder", true));
		return formData;
	}

	@Override
	public void deleteFolderAndChildFolders(Long folderId)
			throws ProcessingException {
		// select all child folders and delete them recursively
		LongArrayHolder childFolder = new LongArrayHolder();
		SQL.selectInto(
				"SELECT file_folder_id FROM file_folder WHERE parent_folder = :folderId AND is_folder = 'true' INTO :childFolder",
				new NVPair("folderId", folderId), new NVPair("childFolder",
						childFolder));

		for (Long childFolderId : childFolder.getValue()) {
			deleteFolderAndChildFolders(childFolderId);
		}

		// delete remained files in folder
		LongArrayHolder fileIds = new LongArrayHolder();
		SQL.selectInto(
				"SELECT file_folder_id FROM file_folder WHERE parent_folder = :folderId INTO :fileIds",
				new NVPair("folderId", folderId),
				new NVPair("fileIds", fileIds));

		for (Long fileId : fileIds.getValue()) {
			SERVICES.getService(IFileService.class).deleteFile(fileId);
		}

		// delete folder
		deleteFolder(folderId);

	}

	@Override
	public void deleteFolder(Long folderId) throws ProcessingException {
		// delete all freeings
		SQL.delete(
				"DELETE FROM role_file_permission WHERE file_id = :folderId",
				new NVPair("folderId", folderId));

		// delete folder
		SQL.delete("DELETE FROM file_folder WHERE file_folder_id = :folderId",
				new NVPair("folderId", folderId));
	}

	@Override
	public FoldersFormData updateFolder(FoldersFormData formData)
			throws ProcessingException {
		SQL.update(
				"UPDATE file_folder SET name = :name WHERE file_folder_id = :id",
				new NVPair("name", formData.getName().getValue()), new NVPair(
						"id", formData.getFolderId()));
		return formData;
	}

	@Override
	public Long getParentFolderId(Long fileOrFolderId)
			throws ProcessingException {
		LongHolder h = new LongHolder();
		SQL.selectInto(
				"SELECT parent_folder FROM file_folder WHERE file_folder_id = :id INTO :h",
				new NVPair("id", fileOrFolderId), new NVPair("h", h));
		return h.getValue();
	}

	private String getFolderName(Long folderId) throws ProcessingException {
		StringHolder name = new StringHolder();
		SQL.selectInto(
				"SELECT name FROM file_folder WHERE file_folder_id = :folderId INTO :name",
				new NVPair("name", name), new NVPair("folderId", folderId));
		return name.getValue();
	}

	@Override
	public String getFolderHierarchy(Long fileOrFolderId)
			throws ProcessingException {
		StringBuffer sb = new StringBuffer();
		Long parentId = fileOrFolderId;
		while (parentId != null) {
			String name = "";
			if (folderNames.containsKey(parentId)) {
				name = folderNames.get(parentId);
			} else {
				//cache foldername
				name = getFolderName(parentId);
				folderNames.put(parentId, name);
			}
			sb.insert(0, " > " + name);
			parentId = getParentFolderId(parentId);
		}
		sb.replace(0, 2, "");
		return sb.toString();
	}

}
