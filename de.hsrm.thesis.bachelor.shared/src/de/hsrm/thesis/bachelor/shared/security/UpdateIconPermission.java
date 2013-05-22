package de.hsrm.thesis.bachelor.shared.security;

import java.security.BasicPermission;

public class UpdateIconPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public UpdateIconPermission() {
    super(UpdateIconPermission.class.getName());
  }
}
