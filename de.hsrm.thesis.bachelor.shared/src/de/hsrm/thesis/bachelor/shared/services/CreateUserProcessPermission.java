package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class CreateUserProcessPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public CreateUserProcessPermission() {
  super("CreateUserProcess");
  }
}
