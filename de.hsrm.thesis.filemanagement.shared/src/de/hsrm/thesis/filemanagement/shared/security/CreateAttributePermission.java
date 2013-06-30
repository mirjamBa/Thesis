package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class CreateAttributePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public CreateAttributePermission() {
	super("CreateAttribute");
	}
}
