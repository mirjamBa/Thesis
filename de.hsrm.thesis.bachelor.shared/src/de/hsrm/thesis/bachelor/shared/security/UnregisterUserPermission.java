package de.hsrm.thesis.bachelor.shared.security;

import org.eclipse.scout.rt.shared.security.BasicHierarchyPermission;

public class UnregisterUserPermission extends BasicHierarchyPermission {

  private static final long serialVersionUID = 0L;

  public UnregisterUserPermission() {
    super(UnregisterUserPermission.class.getName());

  }
}
