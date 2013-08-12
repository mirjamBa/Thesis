package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority for reading fileformat-filetype mappings.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ReadFileFormatPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public ReadFileFormatPermission() {
		super("ReadFileFormat");
	}
}
