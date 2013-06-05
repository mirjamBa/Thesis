package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class ReadTestPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public ReadTestPermission() {
    super("ReadTest");
  }
}
