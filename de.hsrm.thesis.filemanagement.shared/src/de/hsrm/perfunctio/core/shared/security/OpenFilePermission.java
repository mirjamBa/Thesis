package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to open a file.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class OpenFilePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public OpenFilePermission() {
		super("OpenFile");
	}
}
