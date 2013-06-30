package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class CreateFilePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public CreateFilePermission() {
	super("CreateFile");
	}
}
