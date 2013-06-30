package de.hsrm.thesis.bachelor.server.services.common.security;

import java.security.AllPermission;
import java.security.Permission;
import java.security.Permissions;
import java.util.HashMap;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringArrayHolder;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.commons.osgi.BundleClassDescriptor;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.server.services.common.security.AbstractAccessControlService;
import org.eclipse.scout.rt.shared.security.RemoteServiceAccessPermission;
import org.eclipse.scout.rt.shared.services.common.security.IPermissionService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.shared.services.IRoleProcessService;
import de.hsrm.thesis.filemanagement.shared.services.IStartupService;

public class AccessControlService extends AbstractAccessControlService {
  private static IScoutLogger logger = ScoutLogManager.getLogger(AccessControlService.class);

  @Override
  protected Permissions execLoadPermissions() {
    Permissions permissions = new Permissions();

    Long userNr = (Long) ServerJob.getCurrentSession().getData(IStartupService.USER_NR);

    if (userNr != null) {
      //permissio to call service
      permissions.add(new RemoteServiceAccessPermission("*.shared.*", "*"));
    }

    try {
      if (SERVICES.getService(IRoleProcessService.class).getAdminRoleId().equals(userNr)) {
        permissions.add(new AllPermission());
      }
    }
    catch (ProcessingException e1) {
      e1.printStackTrace();
    }

    StringArrayHolder perm = new StringArrayHolder();
    try {
      SQL.selectInto("SELECT DISTINCT p.permission_name "
          + "         FROM            role_permission p, "
          + "                         user_role r "
          + "         WHERE           r.u_id = :userNr "
          + "         AND             r.role_id = p.role_id "
          + "         INTO            :perm ",
          new NVPair("perm", perm),
          new NVPair("userNr", userNr));

      HashMap<String, String> map = new HashMap<String, String>();
      for (BundleClassDescriptor descriptor : SERVICES.getService(IPermissionService.class).getAllPermissionClasses()) {
        map.put(descriptor.getSimpleClassName(), descriptor.getClassName());
      }

      //instantiate permissions and assign them
      for (String simpleClass : perm.getValue()) {
        try {
          permissions.add((Permission) Class.forName(map.get(simpleClass)).newInstance());
        }
        catch (Exception e) {
          logger.error("cannot find permission " + simpleClass + ": " + e.getMessage());
        }
      }
    }
    catch (ProcessingException e) {
      logger.error("cannot read permissions: " + e.getMessage());
    }

    return permissions;
  }
}
