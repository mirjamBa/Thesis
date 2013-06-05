package de.hsrm.thesis.bachelor.shared.security;

import org.eclipse.scout.rt.shared.security.BasicHierarchyPermission;

public class RegisterUserPermission extends BasicHierarchyPermission {

  private static final long serialVersionUID = 0L;

  public RegisterUserPermission() {
    super(RegisterUserPermission.class.getName());
  }
}
