package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

public class RegisterUserPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public RegisterUserPermission() {
    super(RegisterUserPermission.class.getName());
  }
}
