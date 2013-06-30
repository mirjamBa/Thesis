package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class RemoveAssignToRolePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public RemoveAssignToRolePermission() {
	super("RemoveAssignToRole");
	}
}
