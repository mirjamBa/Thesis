package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class ReadRolePermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public ReadRolePermission() {
    super("ReadRole");
  }
}