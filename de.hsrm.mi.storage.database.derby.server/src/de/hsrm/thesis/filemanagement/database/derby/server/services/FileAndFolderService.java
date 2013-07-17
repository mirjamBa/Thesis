package de.hsrm.thesis.filemanagement.database.derby.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.shared.services.IFileAndFolderFreeingService;
import de.hsrm.thesis.filemanagement.shared.services.IFileAndFolderService;
import de.hsrm.thesis.filemanagement.shared.services.IRoleProcessService;
import de.hsrm.thesis.filemanagement.shared.utility.ArrayUtility;

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
			Long[] oldRoles = SERVICES.getService(IRoleProcessService.class)
					.getApprovedRolesForFileOrFolder(slidedFFId);
			// get new roles for file ore folder
			Long[] newRoles = SERVICES.getService(IRoleProcessService.class)
					.getApprovedRolesForFileOrFolder(newFolderId);

			Long[] toDelete = ArrayUtility.getDifferenz(oldRoles, newRoles);
			Long[] toAdd = ArrayUtility.getDifferenz(newRoles, oldRoles);

			IFileAndFolderFreeingService service = SERVICES
					.getService(IFileAndFolderFreeingService.class);

			service.removeFreeingFromChildFolderAndFiles(slidedFFId, toDelete);
			service.removeFreeingFromFolderOrFile(slidedFFId, toDelete);
			service.addFileFolderFreeing(slidedFFId, toAdd);
			service.addFreeingToChildFolderAndFiles(slidedFFId, toAdd);
		}
	}
}
