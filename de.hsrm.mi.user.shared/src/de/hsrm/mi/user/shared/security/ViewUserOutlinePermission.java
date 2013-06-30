package de.hsrm.mi.user.shared.security;

import java.security.BasicPermission;

public class ViewUserOutlinePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ViewUserOutlinePermission() {
	super("ViewUserOutline");
	}
}
