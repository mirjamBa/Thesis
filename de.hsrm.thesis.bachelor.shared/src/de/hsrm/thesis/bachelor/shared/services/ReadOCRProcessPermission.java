package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class ReadOCRProcessPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public ReadOCRProcessPermission() {
  super("ReadOCRProcess");
  }
}
