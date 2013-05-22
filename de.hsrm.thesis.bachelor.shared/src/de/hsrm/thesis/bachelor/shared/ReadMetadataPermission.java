package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class ReadMetadataPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public ReadMetadataPermission() {
    super("ReadMetadata");
  }
}
