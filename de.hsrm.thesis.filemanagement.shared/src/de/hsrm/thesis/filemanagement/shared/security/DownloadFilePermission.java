package de.hsrm.thesis.filemanagement.shared.security;

import java.security.BasicPermission;

public class DownloadFilePermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public DownloadFilePermission() {
	super("DownloadFile");
	}
}