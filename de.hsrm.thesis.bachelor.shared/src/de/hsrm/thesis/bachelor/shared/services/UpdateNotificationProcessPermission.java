package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class UpdateNotificationProcessPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public UpdateNotificationProcessPermission() {
  super("UpdateNotificationProcess");
  }
}
