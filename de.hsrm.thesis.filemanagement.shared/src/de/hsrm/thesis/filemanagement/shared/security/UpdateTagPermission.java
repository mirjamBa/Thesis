package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class UpdateTagPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public UpdateTagPermission() {
	super("UpdateTag");
	}
}
