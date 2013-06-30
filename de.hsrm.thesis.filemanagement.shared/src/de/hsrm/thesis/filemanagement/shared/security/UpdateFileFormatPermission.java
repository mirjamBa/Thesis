package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class UpdateFileFormatPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public UpdateFileFormatPermission() {
	super("UpdateFileFormat");
	}
}
