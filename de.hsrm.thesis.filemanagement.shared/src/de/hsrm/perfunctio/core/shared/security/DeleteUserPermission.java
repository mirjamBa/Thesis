package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;


public class DeleteUserPermission extends BasicPermission {

  private static final long serialVersionUID = 0L;

  public DeleteUserPermission() {
    super(DeleteUserPermission.class.getName());
  }
}