package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class CreateTagPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public CreateTagPermission() {
    super("CreateTag");
  }
}
