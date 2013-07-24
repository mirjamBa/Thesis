package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

public class UpdateFolderPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public UpdateFolderPermission() {
	super("UpdateFolder");
	}
}
