package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

public class OpenFilePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public OpenFilePermission() {
	super("OpenFile");
	}
}
