package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class CreateTagPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public CreateTagPermission() {
    super("CreateTag");
  }
}
