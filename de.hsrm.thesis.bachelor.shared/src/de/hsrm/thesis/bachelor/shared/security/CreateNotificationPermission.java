package de.hsrm.thesis.bachelor.shared.security;

import java.security.BasicPermission;

public class CreateNotificationPermission extends BasicPermission{

  private static final long serialVersionUID = 0L;

  public CreateNotificationPermission() {
  super("CreateNotification");
  }
}
