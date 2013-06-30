package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class DeleteRolePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public DeleteRolePermission() {
	super("DeleteRole");
	}
}
