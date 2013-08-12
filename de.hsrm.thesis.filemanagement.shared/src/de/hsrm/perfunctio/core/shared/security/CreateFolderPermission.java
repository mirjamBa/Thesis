package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to create a new folder.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class CreateFolderPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public CreateFolderPermission() {
		super("CreateFolder");
	}
}
