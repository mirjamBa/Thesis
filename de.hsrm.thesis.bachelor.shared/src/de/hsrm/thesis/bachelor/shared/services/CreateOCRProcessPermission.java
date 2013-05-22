package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class CreateOCRProcessPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public CreateOCRProcessPermission() {
  super("CreateOCRProcess");
  }
}
