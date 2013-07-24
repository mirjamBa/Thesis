package de.hsrm.perfunctio.database.derby.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.security.CreateRolePermission;
import de.hsrm.perfunctio.core.shared.security.DeleteRolePermission;
import de.hsrm.perfunctio.core.shared.security.ReadRolesPermission;
import de.hsrm.perfunctio.core.shared.security.UpdateRolePermission;
import de.hsrm.perfunctio.core.shared.services.IRolePermissionService;
import de.hsrm.perfunctio.core.shared.services.IRoleProcessService;
import de.hsrm.perfunctio.core.shared.services.IStartupService;
import de.hsrm.perfunctio.core.shared.services.formdata.RoleFormData;

public class RoleProcessService extends AbstractService
		implements
			IRoleProcessService {

	@Override
	public Object[][] getRoles() throws ProcessingException {
		if (!ACCESS.check(new ReadRolesPermission())) {
			throw new VetoException(TEXTS.get("VETORolesFilePermission"));
		}
		return SQL.select("SELECT role_id, name FROM role");
	}

	@Override
	public RoleFormData create(RoleFormData formData)
			throws ProcessingException {
		if (!ACCESS.check(new CreateRolePermission())) {
			throw new VetoException(TEXTS.get("VETOCreateRolePermission"));
		}

		SQL.insert(
				"INSERT INTO role (name, user_creator_id) VALUES(:rolename, :creator)",
				new NVPair("rolename", formData.getName()), new NVPair(
						"creator", (Long) ServerJob.getCurrentSession()
								.getData(IStartupService.USER_NR)));

		LongHolder roleId = new LongHolder();

		SQL.selectInto(
				"SELECT role_id FROM role WHERE name = :name AND user_creator_id = :id INTO :roleId",
				new NVPair("name", formData.getName()),
				new NVPair("id", (Long) ServerJob.getCurrentSession().getData(
						IStartupService.USER_NR)), new NVPair("roleId", roleId));

		// insert permissions for roles
		if (formData.getPermissions().getValue() != null) {
			SQL.insert(
					"INSERT INTO role_permission (role_id, permission_name) VALUES (:roleId, :{permissions})",
					new NVPair("roleId", roleId.getValue()),
					new NVPair("permissions", formData.getPermissions()
							.getValue()));
		}
		return formData;
	}

	@Override
	public void deleteRoles(Long[] roleIds) throws ProcessingException {
		if (!ACCESS.check(new DeleteRolePermission())) {
			throw new VetoException(TEXTS.get("VETODeleteRolePermission"));
		}
		SQL.delete("DELETE FROM role_file_permission WHERE role_id = :ids",
				new NVPair("ids", roleIds));

		SQL.delete("DELETE FROM user_role WHERE role_id = :ids", new NVPair(
				"ids", roleIds));

		SQL.delete("DELETE FROM role_permission WHERE role_id = :ids",
				new NVPair("ids", roleIds));

		SQL.delete("DELETE FROM role WHERE role_id = :ids", new NVPair("ids",
				roleIds));
	}

	@Override
	public RoleFormData update(RoleFormData formData)
			throws ProcessingException {
		if (!ACCESS.check(new UpdateRolePermission())) {
			throw new VetoException(TEXTS.get("VETOUpdateRolePermission"));
		}
		SQL.update("UPDATE role SET name = :roleName WHERE role_id = :roleId",
				new NVPair("roleName", formData.getName().getValue()),
				new NVPair("roleId", formData.getRoleId()));

		IRolePermissionService service = SERVICES
				.getService(IRolePermissionService.class);
		service.deletePermissions(formData.getRoleId());
		service.insertPermissions(formData.getRoleId(), formData
				.getPermissions().getValue());

		return formData;
	}

	@Override
	public void deleteRolesForFile(Long fileId) throws ProcessingException {
		SQL.delete("DELETE FROM role_file_permission WHERE file_id = :fileId",
				new NVPair("fileId", fileId));
	}

	@Override
	public Long getRoleId(String name) throws ProcessingException {
		LongHolder idHolder = new LongHolder();
		SQL.selectInto(
				"SELECT role_id FROM role WHERE UPPER(name) = UPPER(:rolename) INTO :id",
				new NVPair("rolename", name), new NVPair("id", idHolder));

		return idHolder.getValue();
	}

	@Override
	public Long getAdminRoleId() throws ProcessingException {
		return getRoleId(USER_ROLE_ADMIN);
	}

	@Override
	public Long getUserRoleId() throws ProcessingException {
		return getRoleId(USER_ROLE_USER);
	}

}
