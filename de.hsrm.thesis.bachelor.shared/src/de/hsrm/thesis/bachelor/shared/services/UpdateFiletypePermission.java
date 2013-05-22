package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class UpdateFiletypePermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public UpdateFiletypePermission() {
  super("UpdateFiletype");
  }
}
