package de.hsrm.thesis.bachelor.shared;

import java.security.BasicPermission;

public class CreateMetadataPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public CreateMetadataPermission() {
    super("CreateMetadata");
  }
}