package de.hsrm.mi.user.shared.security;

import java.security.BasicPermission;

public class ViewRoleTablePagePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ViewRoleTablePagePermission() {
	super("ViewRoleTablePage");
	}
}
