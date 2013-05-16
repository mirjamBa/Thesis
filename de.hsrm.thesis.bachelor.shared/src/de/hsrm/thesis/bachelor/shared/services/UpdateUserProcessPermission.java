package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class UpdateUserProcessPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public UpdateUserProcessPermission() {
  super("UpdateUserProcess");
  }
}
