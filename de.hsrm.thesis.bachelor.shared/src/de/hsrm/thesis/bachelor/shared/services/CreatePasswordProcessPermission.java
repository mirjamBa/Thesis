package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class CreatePasswordProcessPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public CreatePasswordProcessPermission() {
  super("CreatePasswordProcess");
  }
}
