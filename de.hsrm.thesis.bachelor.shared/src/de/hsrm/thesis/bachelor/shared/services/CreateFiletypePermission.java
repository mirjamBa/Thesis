package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class CreateFiletypePermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public CreateFiletypePermission() {
  super("CreateFiletype");
  }
}
