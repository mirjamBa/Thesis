package de.hsrm.thesis.filemanagement.shared.services;

import java.security.BasicPermission;

public class ReadFolderPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ReadFolderPermission() {
	super("ReadFolder");
	}
}
