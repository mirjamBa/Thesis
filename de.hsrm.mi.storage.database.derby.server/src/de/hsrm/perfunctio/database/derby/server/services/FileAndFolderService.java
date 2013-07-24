package de.hsrm.perfunctio.database.derby.server.services;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.services.IFileAndFolderFreeingService;
import de.hsrm.perfunctio.core.shared.services.IFileAndFolderService;
import de.hsrm.perfunctio.core.shared.utility.RolePermissionUtility;

public class FileAndFolderService extends AbstractService
		implements
			IFileAndFolderService {
	@Override
	public void slideFileOrFolder(Long slidedFFId, Long oldFolderId,
			Long newFolderId) throws ProcessingException {
		System.out.println("SLIDE " + slidedFFId + " FROM " + oldFolderId
				+ " TO " + newFolderId);
		if (oldFolderId != newFolderId) {
			// update parent folder id for shifted file/folder
			SQL.update(
					"UPDATE file_folder SET parent_folder = :newFolder WHERE file_folder_id = :fileId",
					new NVPair("newFolder", newFolderId), new NVPair("fileId",
							slidedFFId));

			// get old roles for file ore folder
			Map<Long, ArrayList<String>> oldRoles = SERVICES.getService(
					IFileAndFolderFreeingService.class)
					.getRolePermissionsForFileOrFolder(slidedFFId);

			// get new roles for file ore folder
			Map<Long, ArrayList<String>> newRoles = SERVICES.getService(
					IFileAndFolderFreeingService.class)
					.getRolePermissionsForFileOrFolder(newFolderId);

			//calculate permissions to add and to delete from slided file or folder
			Map<Long, ArrayList<String>> toDelete = RolePermissionUtility
					.getRolePermissionDif(newRoles, oldRoles);
			Map<Long, ArrayList<String>> toAdd = RolePermissionUtility
					.getRolePermissionDif(oldRoles, newRoles);

			IFileAndFolderFreeingService service = SERVICES
					.getService(IFileAndFolderFreeingService.class);

			//delete old permissions
			service.removeFreeingFromChildFolderAndFiles(slidedFFId, toDelete);
			service.removeFreeingFromFolderOrFile(slidedFFId, toDelete);

			//add new permissions
			service.addFileFolderFreeing(slidedFFId,
					toAdd.keySet().toArray(new Long[toAdd.keySet().size()]),
					toAdd);
			service.addFreeingToChildFolderAndFiles(slidedFFId, toAdd.keySet()
					.toArray(new Long[toAdd.keySet().size()]), toAdd);
		}
	}
}
