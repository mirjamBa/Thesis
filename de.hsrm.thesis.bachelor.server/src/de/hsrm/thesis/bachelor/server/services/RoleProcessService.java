package de.hsrm.thesis.bachelor.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.LongArrayHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.server.ServerSession;
import de.hsrm.thesis.filemanagement.shared.formdata.RoleFormData;
import de.hsrm.thesis.filemanagement.shared.services.IRoleProcessService;
import de.hsrm.thesis.filemanagement.shared.services.IUserProcessService;

public class RoleProcessService extends AbstractService implements IRoleProcessService {

  @Override
  public Object[][] getRoles() throws ProcessingException {
    return SQL.select("SELECT role_id, name FROM role");
  }

  @Override
  public RoleFormData create(RoleFormData formData) throws ProcessingException {

    SQL.insert("INSERT INTO role (name, user_creator_id) VALUES(:rolename, :creator)",
        new NVPair("rolename", formData.getName()),
        new NVPair("creator", SERVICES.getService(IUserProcessService.class).getUserId(ServerSession.get().getUserId())));
    return formData;
  }

  @Override
  public Long[] getApprovedRolesForFile(Long fileId) throws ProcessingException {
    LongArrayHolder longArrayHolder = new LongArrayHolder();
    SQL.selectInto("SELECT role_id FROM role_file_permission WHERE file_id = :fileId INTO :longArray",
        new NVPair("fileId", fileId),
        new NVPair("longArray", longArrayHolder));
    return longArrayHolder.getValue();
  }

  @Override
  public void deleteRoles(Long[] roleIds) throws ProcessingException {
    SQL.delete("DELETE FROM role_file_permission WHERE role_id = :ids",
        new NVPair("ids", roleIds));

    SQL.delete("DELETE FROM user_role WHERE role_id = :ids",
        new NVPair("ids", roleIds));

    SQL.delete("DELETE FROM role WHERE role_id = :ids",
        new NVPair("ids", roleIds));
  }

  @Override
  public RoleFormData update(RoleFormData formData) throws ProcessingException {
    SQL.update("UPDATE role SET name = :roleName WHERE role_id = :roleId",
        new NVPair("roleName", formData.getName().getValue()),
        new NVPair("roleId", formData.getRoleId()));
    return formData;
  }

  @Override
  public void deleteRolesForFile(Long fileId) throws ProcessingException {
    SQL.delete("DELETE FROM role_file_permission WHERE file_id = :fileId", new NVPair("fileId", fileId));
  }
}
