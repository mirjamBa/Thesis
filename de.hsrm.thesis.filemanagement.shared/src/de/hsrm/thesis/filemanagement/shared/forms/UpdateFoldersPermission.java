package de.hsrm.thesis.filemanagement.shared.forms;

import java.security.BasicPermission;

public class UpdateFoldersPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public UpdateFoldersPermission() {
		super("UpdateFolders");
	}
}
