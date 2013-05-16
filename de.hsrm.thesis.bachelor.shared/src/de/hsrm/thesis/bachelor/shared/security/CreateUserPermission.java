package de.hsrm.thesis.bachelor.shared.security;

import java.security.BasicPermission;

public class CreateUserPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public CreateUserPermission() {
    super(CreateUserPermission.class.getName());
  }
}
