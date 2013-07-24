package de.hsrm.perfunctio.user.shared.security;

import java.security.BasicPermission;

public class ViewPermissionTablePagePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ViewPermissionTablePagePermission() {
	super("ViewPermissionTablePage");
	}
}
