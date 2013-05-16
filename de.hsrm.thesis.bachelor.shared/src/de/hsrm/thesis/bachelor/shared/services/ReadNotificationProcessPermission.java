package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class ReadNotificationProcessPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public ReadNotificationProcessPermission() {
  super("ReadNotificationProcess");
  }
}
