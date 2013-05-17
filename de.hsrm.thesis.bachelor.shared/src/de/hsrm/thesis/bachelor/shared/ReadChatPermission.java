package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class ReadChatPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public ReadChatPermission() {
    super("ReadChat");
  }
}
