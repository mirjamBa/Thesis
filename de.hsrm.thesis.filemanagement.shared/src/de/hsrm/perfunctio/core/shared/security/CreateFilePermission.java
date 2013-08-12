package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to add a new file.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class CreateFilePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public CreateFilePermission() {
		super("CreateFile");
	}
}
