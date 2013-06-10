package de.hsrm.thesis.bachelor.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.LongArrayHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.server.ServerSession;
import de.hsrm.thesis.bachelor.server.util.UserUtility;
import de.hsrm.thesis.bachelor.shared.RoleFormData;
import de.hsrm.thesis.bachelor.shared.services.IRoleProcessService;

public class RoleProcessService extends AbstractService implements IRoleProcessService {

  @Override
  public Object[][] getRoles() throws ProcessingException {
    return SQL.select("SELECT role_id, name FROM role");
  }

  @Override
  public RoleFormData create(RoleFormData formData) throws ProcessingException {

    SQL.insert("INSERT INTO role (name, user_creator_id) VALUES(:rolename, :creator)",
        new NVPair("rolename", formData.getName()),
        new NVPair("creator", UserUtility.getUserId(ServerSession.get().getUserId())));
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
}
