package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority for deleting a file.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class DeleteFilePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public DeleteFilePermission() {
		super("DeleteFile");
	}
}
