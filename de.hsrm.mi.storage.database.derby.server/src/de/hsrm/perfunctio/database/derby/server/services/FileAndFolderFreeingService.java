package de.hsrm.perfunctio.database.derby.server.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.LongArrayHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.security.FreeFilePermission;
import de.hsrm.perfunctio.core.shared.security.ReadFilePermission;
import de.hsrm.perfunctio.core.shared.security.ReadFolderPermission;
import de.hsrm.perfunctio.core.shared.services.IFileAndFolderFreeingService;
import de.hsrm.perfunctio.core.shared.services.IFolderService;

public class FileAndFolderFreeingService extends AbstractService
		implements
			IFileAndFolderFreeingService {

	@Override
	public void addFreeingToChildFolderAndFiles(Long parentFolderId,
			Long[] roleIds, Map<Long, ArrayList<String>> permissionMapping)
			throws ProcessingException {

		// select child folders
		LongArrayHolder childFolders = new LongArrayHolder();
		SQL.selectInto(
				"SELECT file_folder_id FROM file_folder WHERE parent_folder = :folderId AND is_folder = 'true' INTO :childFolders",
				new NVPair("childFolders", childFolders), new NVPair(
						"folderId", parentFolderId));

		for (Long childId : childFolders.getValue()) {
			// add roles to child folder
			addFileFolderFreeing(childId, roleIds, permissionMapping);
			// free child files and folders
			addFreeingToChildFolderAndFiles(childId, roleIds, permissionMapping);
		}

		// select files
		LongArrayHolder files = new LongArrayHolder();
		SQL.selectInto(
				"SELECT file_folder_id FROM file_folder WHERE parent_folder = :folderId AND is_folder = 'false' INTO :files",
				new NVPair("files", files), new NVPair("folderId",
						parentFolderId));

		for (Long fileId : files.getValue()) {
			addFileFolderFreeing(fileId, roleIds, permissionMapping);
		}

	}

	@Override
	public void addFileFolderFreeing(Long folderId, Long[] roleIds,
			Map<Long, ArrayList<String>> permissionMapping)
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
			for (Long roleId : newRoles) {
				if (permissionMapping != null
						&& permissionMapping.get(roleId) != null) {
					SQL.insert(
							"INSERT INTO role_file_permission (file_id, role_id, permission_name) VALUES (:folderId, :role, :{permission})",
							new NVPair("folderId", folderId), new NVPair(
									"role", roleId), new NVPair("permission",
									permissionMapping.get(roleId)));
				} else {
					SQL.insert(
							"INSERT INTO role_file_permission (file_id, role_id, permission_name) VALUES (:folderId, :role, :{permission})",
							new NVPair("folderId", folderId), new NVPair(
									"role", roleId),
							new NVPair("permission", new String[]{
									new ReadFilePermission().getName(),
									new ReadFolderPermission().getName()}));
				}
			}

		}
	}

	@Override
	public void removeFreeingFromChildFolderAndFiles(Long parentFolderId,
			Map<Long, ArrayList<String>> permissions) throws ProcessingException {
		// select child folders
		LongArrayHolder childFolders = new LongArrayHolder();
		SQL.selectInto(
				"SELECT file_folder_id FROM file_folder WHERE parent_folder = :folderId AND is_folder = 'true' INTO :childFolders",
				new NVPair("childFolders", childFolders), new NVPair(
						"folderId", parentFolderId));

		for (Long childId : childFolders.getValue()) {
			// remove roles from folder
			removeFreeingFromFolderOrFile(childId, permissions);
			// remove roles from child files and folders
			removeFreeingFromChildFolderAndFiles(childId, permissions);
		}

		// select files
		LongArrayHolder files = new LongArrayHolder();
		SQL.selectInto(
				"SELECT file_folder_id FROM file_folder WHERE parent_folder = :folderId AND is_folder = 'false' INTO :files",
				new NVPair("files", files), new NVPair("folderId",
						parentFolderId));

		for (Long fileId : files.getValue()) {
			removeFreeingFromFolderOrFile(fileId, permissions);
		}
	}

	@Override
	public void removeFreeingFromFolderOrFile(Long id,
			Map<Long, ArrayList<String>> permissions)
			throws ProcessingException {
		for (Long role : permissions.keySet()) {
			SQL.delete(
					"DELETE FROM role_file_permission WHERE file_id = :id AND role_id = :role AND permission_name = :{permissions}",
					new NVPair("id", id),
					new NVPair("role", role),
					new NVPair("permissions", permissions.get(role).toArray(
							new String[permissions.get(role).size()])));
		}
	}

	@Override
	public void freeFileOrFolder(Long fileId, Long[] roleIds,
			Map<Long, ArrayList<String>> permissionMapping)
			throws ProcessingException {

		if (!ACCESS.check(new FreeFilePermission())) {
			throw new VetoException(TEXTS.get("VETOFreeFilePermission"));
		}

		SQL.delete("DELETE FROM role_file_permission WHERE file_id= :fileId",
				new NVPair("fileId", fileId));

		if (roleIds != null) {
			// insert selected roles
			for (Long roleId : roleIds) {
				if (permissionMapping.get(roleId) != null) {
					String[] p = permissionMapping.get(roleId)
							.toArray(
									new String[permissionMapping.get(roleId)
											.size() + 1]);
					p[p.length - 1] = new ReadFilePermission().getName();

					SQL.insert(
							"INSERT INTO role_file_permission (file_id, role_id, permission_name) VALUES (:fileId, :roleId, :{permissions})",
							new NVPair("fileId", fileId), new NVPair("roleId",
									roleId), new NVPair("permissions", p));
				} else if (permissionMapping.get(roleId) == null
						|| permissionMapping.get(roleId).size() == 0) {
					SQL.insert(
							"INSERT INTO role_file_permission (file_id, role_id, permission_name) VALUES (:fileId, :roleId :permission)",
							new NVPair("fileId", fileId), new NVPair("roleId",
									roleId), new NVPair("permission",
									new ReadFilePermission().getName()));
				}

			}
		}
	}

	@Override
	public void updateRoleFileAndFolderPermission(Long folderId,
			Long[] roleIds, Map<Long, ArrayList<String>> permissionMapping)
			throws ProcessingException {
		// free file with all specific permission for each role
		freeFileOrFolder(folderId, roleIds, permissionMapping);

		// get all parent folders and add roles
		Long id = new Long(folderId);
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
			// free parent folder with read permissions only
			addFileFolderFreeing(parentFolderId, roleIds, null);
		}
	}

	@Override
	public Long[] getApprovedRolesForFileOrFolder(Long fileId)
			throws ProcessingException {
		LongArrayHolder longArrayHolder = new LongArrayHolder();
		SQL.selectInto(
				"SELECT DISTINCT role_id FROM role_file_permission WHERE file_id = :fileId INTO :longArray",
				new NVPair("fileId", fileId), new NVPair("longArray",
						longArrayHolder));
		return longArrayHolder.getValue();
	}

	@Override
	public Map<Long, ArrayList<String>> getRolePermissionsForFileOrFolder(
			Long fileId) throws ProcessingException {
		Map<Long, ArrayList<String>> map = new HashMap<Long, ArrayList<String>>();

		Object[][] permissions = SQL
				.select("SELECT role_id, permission_name FROM role_file_permission WHERE file_id = :fileId",
						new NVPair("fileId", fileId));

		for (int i = 0; i < permissions.length; i++) {
			Long roleId = (Long) permissions[i][0];
			String permission = (String) permissions[i][1];
			if (!map.containsKey(roleId)) {
				map.put(roleId, new ArrayList<String>());
			}
			map.get(roleId).add(permission);
		}

		return map;
	}

}
