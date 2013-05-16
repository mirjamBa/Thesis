package de.hsrm.thesis.bachelor.shared.services;

import java.security.BasicPermission;

public class CreateNotificationProcessPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public CreateNotificationProcessPermission() {
  super("CreateNotificationProcess");
  }
}
