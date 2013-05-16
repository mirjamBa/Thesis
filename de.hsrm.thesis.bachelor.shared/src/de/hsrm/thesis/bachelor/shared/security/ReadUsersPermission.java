package de.hsrm.thesis.bachelor.shared.security;

import java.security.BasicPermission;

public class ReadUsersPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public ReadUsersPermission() {
    super(ReadUsersPermission.class.getName());
  }
}
