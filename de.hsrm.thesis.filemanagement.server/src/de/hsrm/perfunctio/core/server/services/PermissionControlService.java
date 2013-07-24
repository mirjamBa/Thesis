package de.hsrm.perfunctio.core.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.LongArrayHolder;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.services.IPermissionControlService;
import de.hsrm.perfunctio.core.shared.services.IUserProcessService;
import de.hsrm.perfunctio.core.shared.utility.ArrayUtility;

public class PermissionControlService extends AbstractService
		implements
			IPermissionControlService {

	@Override
	public boolean check(Long currentUser, Object resource, String function)
			throws ProcessingException {
		if (resource != null) {

			// folder or file uploaded by current user?
			LongHolder userId = new LongHolder();
			SQL.selectInto(
					"SELECT u_id FROM file_folder WHERE file_folder_id = :ffId INTO :user ",
					new NVPair("ffId", resource), new NVPair("user", userId));

			if (userId.getValue().equals(currentUser)) {
				// file owner can do everything with his files
				return true;
			}

			LongArrayHolder roles = new LongArrayHolder();
			//FIXME permissions/roles leer, entweder falsch gespeichert oder falsch geladen
			SQL.selectInto(
					"SELECT role_id FROM role_file_permission WHERE file_id = :ffId AND permission_name = :permission INTO :roles",
					new NVPair("ffId", resource), new NVPair("permission",
							function + "Permission"), new NVPair("roles", roles));

			// roles of the user
			Long[] userRoles = SERVICES.getService(IUserProcessService.class)
					.getUserRoles(currentUser);
			// roles the file is approved for an contains the expected
			// function-permission
			Long[] functionRoles = roles.getValue();

			Long[] dif = ArrayUtility.getDifference(functionRoles, userRoles);
			if (dif.length < functionRoles.length) {
				// user roles contains one of the expected roles
				return true;
			}

			return false;
		}
		return true;
	}

}
