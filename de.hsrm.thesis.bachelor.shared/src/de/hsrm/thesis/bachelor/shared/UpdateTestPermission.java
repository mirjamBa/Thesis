package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class UpdateTestPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public UpdateTestPermission() {
    super("UpdateTest");
  }
}
