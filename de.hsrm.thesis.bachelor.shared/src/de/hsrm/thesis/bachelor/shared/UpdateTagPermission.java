package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class UpdateTagPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public UpdateTagPermission() {
    super("UpdateTag");
  }
}
