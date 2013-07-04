package de.hsrm.thesis.filemanagement.shared.services;

import java.security.BasicPermission;

public class CreateFolderPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public CreateFolderPermission() {
	super("CreateFolder");
	}
}
