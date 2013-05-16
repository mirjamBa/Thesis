package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class ReadPasswordProcessPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public ReadPasswordProcessPermission() {
  super("ReadPasswordProcess");
  }
}
