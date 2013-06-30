package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class DeleteFileFormatPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public DeleteFileFormatPermission() {
	super("DeleteFileFormat");
	}
}
