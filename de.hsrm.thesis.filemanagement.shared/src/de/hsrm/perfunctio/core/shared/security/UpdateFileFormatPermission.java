package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to modify a fileformat-filetype mapping
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class UpdateFileFormatPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public UpdateFileFormatPermission() {
		super("UpdateFileFormat");
	}
}
