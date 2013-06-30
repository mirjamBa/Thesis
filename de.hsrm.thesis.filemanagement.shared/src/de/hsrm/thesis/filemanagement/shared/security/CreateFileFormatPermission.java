package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class CreateFileFormatPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public CreateFileFormatPermission() {
	super("CreateFileFormat");
	}
}
