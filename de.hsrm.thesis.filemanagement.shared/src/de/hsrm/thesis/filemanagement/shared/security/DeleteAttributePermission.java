package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class DeleteAttributePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public DeleteAttributePermission() {
	super("DeleteAttribute");
	}
}
