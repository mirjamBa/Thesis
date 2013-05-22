package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class UpdateMetadataPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public UpdateMetadataPermission() {
    super("UpdateMetadata");
  }
}
