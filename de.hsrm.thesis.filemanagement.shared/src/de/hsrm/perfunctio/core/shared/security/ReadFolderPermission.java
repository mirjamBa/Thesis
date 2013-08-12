package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority for reading folders.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ReadFolderPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public ReadFolderPermission() {
		super("ReadFolders");
	}
}
