package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority for deleting a folder.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class DeleteFolderPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public DeleteFolderPermission() {
		super("DeleteFolder");
	}
}
