package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to download a file.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class DownloadFilePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public DownloadFilePermission() {
		super("DownloadFile");
	}
}
