package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class ReadAttributePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ReadAttributePermission() {
	super("ReadAttribute");
	}
}
