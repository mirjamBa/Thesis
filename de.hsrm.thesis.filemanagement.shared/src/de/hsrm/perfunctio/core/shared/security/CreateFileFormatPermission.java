package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to create a new fileformat-filetype mapping.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class CreateFileFormatPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public CreateFileFormatPermission() {
		super("CreateFileFormat");
	}
}
