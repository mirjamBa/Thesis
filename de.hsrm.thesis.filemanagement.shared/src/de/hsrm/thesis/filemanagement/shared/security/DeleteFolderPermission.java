package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class DeleteFolderPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public DeleteFolderPermission() {
	super("DeleteFolder");
	}
}
