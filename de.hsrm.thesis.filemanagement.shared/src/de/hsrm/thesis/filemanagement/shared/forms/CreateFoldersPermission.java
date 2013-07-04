package de.hsrm.thesis.filemanagement.shared.forms;

import java.security.BasicPermission;

public class CreateFoldersPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public CreateFoldersPermission() {
		super("CreateFolders");
	}
}
