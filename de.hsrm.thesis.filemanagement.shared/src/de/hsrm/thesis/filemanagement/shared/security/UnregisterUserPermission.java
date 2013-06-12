package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class UnregisterUserPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public UnregisterUserPermission() {
    super(UnregisterUserPermission.class.getName());

  }
}