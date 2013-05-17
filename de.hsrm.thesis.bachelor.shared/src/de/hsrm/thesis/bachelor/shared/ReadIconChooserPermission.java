package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class ReadIconChooserPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public ReadIconChooserPermission() {
    super("ReadIconChooser");
  }
}
