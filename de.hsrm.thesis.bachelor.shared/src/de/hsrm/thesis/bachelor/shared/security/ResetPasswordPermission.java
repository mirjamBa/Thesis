package de.hsrm.thesis.bachelor.shared.security;

import org.eclipse.scout.rt.shared.security.BasicHierarchyPermission;

public class ResetPasswordPermission extends BasicHierarchyPermission {

  private static final long serialVersionUID = 0L;

  public ResetPasswordPermission() {
    super(ResetPasswordPermission.class.getName());
  }
}
