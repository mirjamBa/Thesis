package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class CreateAssignToRolePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public CreateAssignToRolePermission() {
		super("CreateAssignToRole");
	}
}