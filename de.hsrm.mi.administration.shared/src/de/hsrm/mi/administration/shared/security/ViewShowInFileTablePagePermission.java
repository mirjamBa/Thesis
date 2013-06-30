package de.hsrm.mi.administration.shared.security;

import java.security.BasicPermission;

public class ViewShowInFileTablePagePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ViewShowInFileTablePagePermission() {
	super("ShowInFileTablePage");
	}
}
