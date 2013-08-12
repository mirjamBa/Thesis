package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to modify an attribute.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class UpdateAttributePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public UpdateAttributePermission() {
		super("UpdateAttribute");
	}
}
