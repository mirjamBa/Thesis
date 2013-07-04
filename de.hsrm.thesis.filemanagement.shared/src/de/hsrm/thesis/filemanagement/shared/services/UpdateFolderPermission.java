package de.hsrm.thesis.filemanagement.shared.services;

import java.security.BasicPermission;

public class UpdateFolderPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public UpdateFolderPermission() {
	super("UpdateFolder");
	}
}
