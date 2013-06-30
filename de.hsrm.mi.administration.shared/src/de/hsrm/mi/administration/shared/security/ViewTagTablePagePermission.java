package de.hsrm.mi.administration.shared.security;

import java.security.BasicPermission;

public class ViewTagTablePagePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ViewTagTablePagePermission() {
	super("TagTablePage");
	}
}
