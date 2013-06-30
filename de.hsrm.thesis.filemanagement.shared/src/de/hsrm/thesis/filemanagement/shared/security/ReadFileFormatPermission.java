package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class ReadFileFormatPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public ReadFileFormatPermission() {
	super("ReadFileFormat");
	}
}
