package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class ReadRolesPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ReadRolesPermission() {
	super("ReadRoles");
	}
}