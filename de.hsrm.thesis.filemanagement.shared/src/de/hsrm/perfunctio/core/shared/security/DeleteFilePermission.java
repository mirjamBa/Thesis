package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

public class DeleteFilePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public DeleteFilePermission() {
	super("DeleteFile");
	}
}