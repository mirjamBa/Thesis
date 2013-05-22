package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class UpdateVersionPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public UpdateVersionPermission() {
    super("UpdateVersion");
  }
}
