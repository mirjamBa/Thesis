package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class ResetPasswordPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public ResetPasswordPermission() {
    super(ResetPasswordPermission.class.getName());
  }
}
