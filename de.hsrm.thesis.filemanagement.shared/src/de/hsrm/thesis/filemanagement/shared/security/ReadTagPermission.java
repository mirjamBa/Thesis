package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class ReadTagPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ReadTagPermission() {
	super("ReadTag");
	}
}
