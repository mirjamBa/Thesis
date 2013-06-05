package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class CreateRolePermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public CreateRolePermission() {
    super("CreateRole");
  }
}
