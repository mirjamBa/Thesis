package de.hsrm.thesis.filemanagement.database.derby.server.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.LongArrayHolder;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.IFolderService;
import de.hsrm.thesis.filemanagement.shared.services.IRoleProcessService;
import de.hsrm.thesis.filemanagement.shared.services.IStartupService;
import de.hsrm.thesis.filemanagement.shared.services.formdata.FoldersFormData;
import de.hsrm.thesis.filemanagement.shared.utility.ArrayUtility;

public class FolderService extends AbstractService implements IFolderService {

	@Override
	public Object[][] getFolders(Long folderId) throws ProcessingException {
		StringBuilder s = new StringBuilder();
		s.append("SELECT 	file_folder_id, " + "			name, " + "			u_id, "
				+ "			is_folder " + "	FROM	file_folder " + "	WHERE	1=1 ");
		if (folderId == null) {
			s.append("AND parent_folder IS NULL ");
		} else {
			s.append("AND parent_folder = :folderId ");
		}

		return SQL.select(s.toString(), new NVPair("folderId", folderId));
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
	public void deleteFolderAndChildFolders(Long folderId) throws ProcessingException {
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
	public void deleteFolder(Long folderId) throws ProcessingException{
		//delete all freeings
		SQL.delete("DELETE FROM role_file_permission WHERE file_id = :folderId", new NVPair("folderId", folderId));
		
		//delete folder
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
	public void freeFolder(Long folderId, Long[] roleIds)
			throws ProcessingException {
		SQL.delete("DELETE FROM role_file_permission WHERE file_id= :folderId",
				new NVPair("folderId", folderId));

		if (roleIds != null) {
			// insert selected roles
			for (Long roleId : roleIds) {
				SQL.insert(
						"INSERT INTO role_file_permission (file_id, role_id) VALUES (:folderId, :roleId)",
						new NVPair("folderId", folderId), new NVPair("roleId",
								roleId));
			}
		}

	}

	@Override
	public void addFreeingToChildFolderAndFiles(Long parentFolderId,
			Long[] roleIds) throws ProcessingException {

		// select child folders
		LongArrayHolder childFolders = new LongArrayHolder();
		SQL.selectInto(
				"SELECT file_folder_id FROM file_folder WHERE parent_folder = :folderId AND is_folder = 'true' INTO :childFolders",
				new NVPair("childFolders", childFolders), new NVPair(
						"folderId", parentFolderId));

		for (Long childId : childFolders.getValue()) {
			// add roles to child folder
			addFileFolderFreeing(childId, roleIds);
			// free child files and folders
			addFreeingToChildFolderAndFiles(childId, roleIds);
		}

		// select files
		LongArrayHolder files = new LongArrayHolder();
		SQL.selectInto(
				"SELECT file_folder_id FROM file_folder WHERE parent_folder = :folderId AND is_folder = 'false' INTO :files",
				new NVPair("files", files), new NVPair("folderId",
						parentFolderId));

		for (Long fileId : files.getValue()) {
			addFileFolderFreeing(fileId, roleIds);
		}

	}
	
	private void removeFreeingFromFolderOrFile(Long id, Long[] roles) throws ProcessingException{
		SQL.delete("DELETE FROM role_file_permission WHERE file_id = :id AND role_id = :{roles}", 
				new NVPair("id", id),
				new NVPair("roles", roles));
	}

	@Override
	public void removeFreeingFromChildFolderAndFiles(Long parentFolderId,
			Long[] roleIds) throws ProcessingException {
		// select child folders
		LongArrayHolder childFolders = new LongArrayHolder();
		SQL.selectInto(
				"SELECT file_folder_id FROM file_folder WHERE parent_folder = :folderId AND is_folder = 'true' INTO :childFolders",
				new NVPair("childFolders", childFolders), new NVPair(
						"folderId", parentFolderId));

		for (Long childId : childFolders.getValue()) {
			//remove roles from folder
			removeFreeingFromFolderOrFile(childId, roleIds);
			// remove roles from child files and folders
			removeFreeingFromChildFolderAndFiles(childId, roleIds);
		}

		// select files
		LongArrayHolder files = new LongArrayHolder();
		SQL.selectInto(
				"SELECT file_folder_id FROM file_folder WHERE parent_folder = :folderId AND is_folder = 'false' INTO :files",
				new NVPair("files", files), new NVPair("folderId",
						parentFolderId));

		for (Long fileId : files.getValue()) {
			removeFreeingFromFolderOrFile(fileId, roleIds);
		}
	}

	@Override
	public Long getParentFolder(Long fileOrFolderId) throws ProcessingException {
		LongHolder h = new LongHolder();
		SQL.selectInto(
				"SELECT parent_folder FROM file_folder WHERE file_folder_id = :id INTO :h",
				new NVPair("id", fileOrFolderId), new NVPair("h", h));
		return h.getValue();
	}

	@Override
	public void addFileFolderFreeing(Long folderId, Long[] roleIds)
			throws ProcessingException {
		if (roleIds != null) {
			// get existent roles
			LongArrayHolder lh = new LongArrayHolder();
			SQL.selectInto(
					"SELECT role_Id FROM role_file_permission WHERE file_id = :folderId INTO :roleId",
					new NVPair("folderId", folderId), new NVPair("roleId", lh));

			// remove existent roles
			List<Long> newRoles = new ArrayList<Long>(Arrays.asList(roleIds));
			newRoles.removeAll(Arrays.asList(lh.getValue()));

			// insert new roles
			SQL.insert(
					"INSERT INTO role_file_permission VALUES (:folderId, :{roles})",
					new NVPair("folderId", folderId), new NVPair("roles",
							newRoles));

		}
	}

	@Override
	public void slideFileOrFolder(Long slidedFFId, Long oldFolderId, Long newFolderId) throws ProcessingException{
	  System.out.println("SLIDE " + slidedFFId + " FROM " + oldFolderId + " TO " + newFolderId);
	  if(oldFolderId != newFolderId){
		  //update parent folder id for shifted file/folder
		  SQL.update("UPDATE file_folder SET parent_folder = :newFolder WHERE file_folder_id = :fileId", 
				  new NVPair("newFolder", newFolderId),
				  new NVPair("fileId", slidedFFId));
		  
		  //get old roles for file ore folder
		  Long[] oldRoles = SERVICES.getService(IRoleProcessService.class).getApprovedRolesForFileOrFolder(slidedFFId);
		  //get new roles for file ore folder
		  Long[] newRoles = SERVICES.getService(IRoleProcessService.class).getApprovedRolesForFileOrFolder(newFolderId);
		  
		  Long[] toDelete = ArrayUtility.getDifferenz(oldRoles, newRoles);
		  Long[] toAdd = ArrayUtility.getDifferenz(newRoles, oldRoles);
		  
		  removeFreeingFromChildFolderAndFiles(slidedFFId, toDelete);
		  removeFreeingFromFolderOrFile(slidedFFId, toDelete);
		  addFileFolderFreeing(slidedFFId, toAdd);
		  addFreeingToChildFolderAndFiles(slidedFFId, toAdd);
	  }
	}
	
}
