package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class UpdateIconChooserPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public UpdateIconChooserPermission() {
    super("UpdateIconChooser");
  }
}
