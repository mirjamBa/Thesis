package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class ReadFoldersPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public ReadFoldersPermission() {
		super("ReadFolders");
	}
}
