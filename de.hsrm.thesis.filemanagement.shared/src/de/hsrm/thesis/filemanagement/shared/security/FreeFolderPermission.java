package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class FreeFolderPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public FreeFolderPermission() {
	super("FreeFolder");
	}
}
