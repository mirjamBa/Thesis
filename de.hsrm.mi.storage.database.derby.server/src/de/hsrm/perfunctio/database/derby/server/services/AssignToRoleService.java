package de.hsrm.perfunctio.database.derby.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.rt.shared.services.common.security.IAccessControlService;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.security.CreateAssignToRolePermission;
import de.hsrm.perfunctio.core.shared.security.RemoveAssignToRolePermission;
import de.hsrm.perfunctio.core.shared.services.IAssignToRoleService;
import de.hsrm.perfunctio.core.shared.services.formdata.AssignToRoleFormData;

public class AssignToRoleService extends AbstractService
		implements
			IAssignToRoleService {

	@Override
	public AssignToRoleFormData create(AssignToRoleFormData formData)
			throws ProcessingException {
		if (!ACCESS.check(new CreateAssignToRolePermission())) {
			throw new VetoException(
					TEXTS.get("VETOCreateAssignToRolePermission"));
		}
		SQL.insert("INSERT INTO role_permission "
				+ "		(role_id, permission_name) "
				+ "		VALUES (:role, :{permission})", formData);
		SERVICES.getService(IAccessControlService.class).clearCache();
		return formData;
	}

	@Override
	public void remove(Long roleId, String[] permission)
			throws ProcessingException {
		if (!ACCESS.check(new RemoveAssignToRolePermission())) {
			throw new VetoException(
					TEXTS.get("VETORemoveAssignToRolePermission"));
		}
		SQL.delete("DELETE FROM role_permission " + "	WHERE role_id = :roleId "
				+ "	AND permissions_name = :{permission} ", new NVPair(
				"roleId", roleId), new NVPair("permission", permission));
		SERVICES.getService(IAccessControlService.class).clearCache();

	}
}
