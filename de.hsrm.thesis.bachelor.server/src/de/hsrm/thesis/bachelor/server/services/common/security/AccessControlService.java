package de.hsrm.thesis.bachelor.server.services.common.security;

import java.security.Permissions;

import org.eclipse.scout.rt.server.services.common.security.AbstractAccessControlService;
import org.eclipse.scout.rt.shared.security.RemoteServiceAccessPermission;
import org.eclipse.scout.rt.shared.services.common.code.ICode;

import de.hsrm.thesis.bachelor.server.ServerSession;
import de.hsrm.thesis.bachelor.shared.security.CreateNotificationPermission;
import de.hsrm.thesis.bachelor.shared.security.UpdateIconPermission;
import de.hsrm.thesis.bachelor.shared.services.code.UserRoleCodeType.AdministratorCode;
import de.hsrm.thesis.bachelor.shared.services.code.UserRoleCodeType.UserCode;
import de.hsrm.thesis.filemanagement.shared.security.CreateUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.DeleteUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.ReadUsersPermission;
import de.hsrm.thesis.filemanagement.shared.security.RegisterUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.ResetPasswordPermission;
import de.hsrm.thesis.filemanagement.shared.security.UnregisterUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.UpdateUserPermission;

public class AccessControlService extends AbstractAccessControlService {

  @Override
  protected Permissions execLoadPermissions() {
    Permissions permissions = new Permissions();

    ICode<Integer> permission = ServerSession.get().getPermission();
    if (permission != null) {
      // USERS
      if (permission.getId() >= UserCode.ID) {
        permissions.add(new RemoteServiceAccessPermission("*.shared.*", "*"));

        permissions.add(new CreateNotificationPermission());
        permissions.add(new ReadUsersPermission());
        permissions.add(new RegisterUserPermission());
        permissions.add(new UnregisterUserPermission());
        permissions.add(new UpdateIconPermission());
      }

      // ADMIN
      if (permission.getId() >= AdministratorCode.ID) {
        permissions.add(new CreateUserPermission());
        permissions.add(new DeleteUserPermission());
        permissions.add(new ResetPasswordPermission());
        permissions.add(new UpdateUserPermission());
      }
    }

    return permissions;
  }

}
