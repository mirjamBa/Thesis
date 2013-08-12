package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to free a folder.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class FreeFolderPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public FreeFolderPermission() {
		super("FreeFolder");
	}
}
