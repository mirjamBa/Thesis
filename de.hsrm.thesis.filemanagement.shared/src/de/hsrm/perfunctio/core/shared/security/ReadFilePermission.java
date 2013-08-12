package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority for reading files.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ReadFilePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public ReadFilePermission() {
		super("ReadFile");
	}
}
