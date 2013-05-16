package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class ReadUserProcessPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public ReadUserProcessPermission() {
  super("ReadUserProcess");
  }
}
