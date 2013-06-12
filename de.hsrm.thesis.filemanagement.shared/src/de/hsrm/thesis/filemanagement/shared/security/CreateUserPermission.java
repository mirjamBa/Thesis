package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class CreateUserPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public CreateUserPermission() {
    super(CreateUserPermission.class.getName());
  }
}
