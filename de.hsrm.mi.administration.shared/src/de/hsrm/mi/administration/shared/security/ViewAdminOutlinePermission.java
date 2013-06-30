package de.hsrm.mi.administration.shared.security;

import java.security.BasicPermission;

public class ViewAdminOutlinePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ViewAdminOutlinePermission() {
	super("AdminOutline");
	}
}
