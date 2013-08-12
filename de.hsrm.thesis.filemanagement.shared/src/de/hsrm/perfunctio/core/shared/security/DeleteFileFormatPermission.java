package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority for deleting a fileformat-filetype mapping.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class DeleteFileFormatPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public DeleteFileFormatPermission() {
		super("DeleteFileFormat");
	}
}
