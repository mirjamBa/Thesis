package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class CreateTestPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public CreateTestPermission() {
    super("CreateTest");
  }
}
