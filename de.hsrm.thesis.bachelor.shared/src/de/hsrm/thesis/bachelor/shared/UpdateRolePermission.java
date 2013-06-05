package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class UpdateRolePermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public UpdateRolePermission() {
    super("UpdateRole");
  }
}
