package de.hsrm.mi.storage.database.derby.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.filemanagement.shared.services.IRolePermissionService;

public class RolePermissionService extends AbstractService implements IRolePermissionService {

  @Override
  public Object[][] getPermissions(Long roleId) throws ProcessingException {
    return SQL.select("SELECT permission_name "
        + "            FROM   role_permission "
        + "            WHERE role_id = :roleId ",
        new NVPair("roleId", roleId));
  }
}