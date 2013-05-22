package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class UpdateOCRProcessPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public UpdateOCRProcessPermission() {
  super("UpdateOCRProcess");
  }
}
