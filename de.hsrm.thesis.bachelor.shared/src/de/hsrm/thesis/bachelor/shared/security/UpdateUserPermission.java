package de.hsrm.thesis.bachelor.shared.security;

import java.security.BasicPermission;

public class UpdateUserPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public UpdateUserPermission() {
    super(UpdateUserPermission.class.getName());
  }
}
