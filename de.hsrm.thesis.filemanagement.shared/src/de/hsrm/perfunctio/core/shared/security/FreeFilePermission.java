package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to free a file.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class FreeFilePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public FreeFilePermission() {
		super("FreeFile");
	}
}
