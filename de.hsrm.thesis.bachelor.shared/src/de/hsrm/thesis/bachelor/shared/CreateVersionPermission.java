package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class CreateVersionPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public CreateVersionPermission() {
    super("CreateVersion");
  }
}
