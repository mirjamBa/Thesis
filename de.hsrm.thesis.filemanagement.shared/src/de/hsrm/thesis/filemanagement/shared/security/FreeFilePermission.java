package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class FreeFilePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public FreeFilePermission() {
	super("FreeFile");
	}
}
