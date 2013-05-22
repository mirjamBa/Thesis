package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class ReadFiletypePermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public ReadFiletypePermission() {
  super("ReadFiletype");
  }
}
