package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to modify folders.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class UpdateFolderPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public UpdateFolderPermission() {
		super("UpdateFolder");
	}
}
