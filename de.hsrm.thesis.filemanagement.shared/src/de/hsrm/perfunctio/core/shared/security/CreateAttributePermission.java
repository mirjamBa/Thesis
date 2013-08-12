package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to create a new attribute.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class CreateAttributePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public CreateAttributePermission() {
		super("CreateAttribute");
	}
}
