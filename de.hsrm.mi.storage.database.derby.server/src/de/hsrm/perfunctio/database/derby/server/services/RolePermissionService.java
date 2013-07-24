package de.hsrm.perfunctio.database.derby.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.perfunctio.core.shared.services.IRolePermissionService;

public class RolePermissionService extends AbstractService
		implements
			IRolePermissionService {

	@Override
	public Object[][] getPermissions(Long roleId) throws ProcessingException {
		return SQL.select("SELECT permission_name "
				+ "            FROM   role_permission "
				+ "            WHERE role_id = :roleId ", new NVPair("roleId",
				roleId));
	}

	@Override
	public void deletePermissions(Long roleId) throws ProcessingException {
		SQL.delete("DELETE FROM role_permission WHERE role_id = :id",
				new NVPair("id", roleId));
	}

	@Override
	public void insertPermissions(Long roleId, String[] permissions)
			throws ProcessingException {
		SQL.insert(
				"INSERT INTO role_permission (role_id, permission_name) VALUES (:roleId, :{permissions})",
				new NVPair("roleId", roleId), new NVPair("permissions",
						permissions));

	}
}
