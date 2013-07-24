package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

public class ReadFilePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ReadFilePermission() {
	super("ReadFile");
	}
}
