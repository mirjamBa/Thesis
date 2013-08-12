package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to modify a tag
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class UpdateTagPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public UpdateTagPermission() {
		super("UpdateTag");
	}
}
