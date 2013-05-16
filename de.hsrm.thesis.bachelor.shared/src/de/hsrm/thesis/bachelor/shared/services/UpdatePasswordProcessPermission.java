package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class UpdatePasswordProcessPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public UpdatePasswordProcessPermission() {
  super("UpdatePasswordProcess");
  }
}
