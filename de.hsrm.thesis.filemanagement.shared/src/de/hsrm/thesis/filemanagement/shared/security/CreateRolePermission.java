package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class CreateRolePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public CreateRolePermission() {
	super("CreateRole");
	}
}