package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class ReadVersionPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public ReadVersionPermission() {
    super("ReadVersion");
  }
}
