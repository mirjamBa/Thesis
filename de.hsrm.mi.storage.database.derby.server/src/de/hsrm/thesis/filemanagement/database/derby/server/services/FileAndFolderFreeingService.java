package de.hsrm.thesis.filemanagement.database.derby.server.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.LongArrayHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.shared.security.FreeFilePermission;
import de.hsrm.thesis.filemanagement.shared.services.IFileAndFolderFreeingService;
import de.hsrm.thesis.filemanagement.shared.services.IFolderService;

public class FileAndFolderFreeingService extends AbstractService
		implements
			IFileAndFolderFreeingService {

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
	public void removeFreeingFromChildFolderAndFiles(Long parentFolderId,
			Long[] roleIds) throws ProcessingException {
		// select child folders
		LongArrayHolder childFolders = new LongArrayHolder();
		SQL.selectInto(
				"SELECT file_folder_id FROM file_folder WHERE parent_folder = :folderId AND is_folder = 'true' INTO :childFolders",
				new NVPair("childFolders", childFolders), new NVPair(
						"folderId", parentFolderId));

		for (Long childId : childFolders.getValue()) {
			// remove roles from folder
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
	public void removeFreeingFromFolderOrFile(Long id, Long[] roles)
			throws ProcessingException {
		SQL.delete(
				"DELETE FROM role_file_permission WHERE file_id = :id AND role_id = :{roles}",
				new NVPair("id", id), new NVPair("roles", roles));
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
					.getParentFolderId(id);
			id = parent;
			if (id != null) {
				parentFolder.add(parent);
			}
		}

		for (Long parentFolderId : parentFolder) {
			SERVICES.getService(IFileAndFolderFreeingService.class).addFileFolderFreeing(
					parentFolderId, roleIds);
		}

	}

}
