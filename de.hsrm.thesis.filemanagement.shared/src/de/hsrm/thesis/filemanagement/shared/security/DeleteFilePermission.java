package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class DeleteFilePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public DeleteFilePermission() {
	super("DeleteFile");
	}
}
