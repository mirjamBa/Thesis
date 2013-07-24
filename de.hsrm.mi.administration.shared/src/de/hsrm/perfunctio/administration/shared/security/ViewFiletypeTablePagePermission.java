package de.hsrm.perfunctio.administration.shared.security;

import java.security.BasicPermission;

public class ViewFiletypeTablePagePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ViewFiletypeTablePagePermission() {
	super("FiletypeTablePage");
	}
}
