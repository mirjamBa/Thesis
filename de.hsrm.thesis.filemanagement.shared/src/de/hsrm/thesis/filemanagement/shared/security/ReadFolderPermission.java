package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class ReadFolderPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public ReadFolderPermission() {
		super("ReadFolders");
	}
}
