package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class UpdateFilePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public UpdateFilePermission() {
	super("ModifyFile");
	}
}
