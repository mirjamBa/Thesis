package de.hsrm.thesis.bachelor.shared.security;

import org.eclipse.scout.rt.shared.security.BasicHierarchyPermission;

public class UpdateIconPermission extends BasicHierarchyPermission {

  private static final long serialVersionUID = 0L;

  public UpdateIconPermission() {
    super(UpdateIconPermission.class.getName());
  }
}
