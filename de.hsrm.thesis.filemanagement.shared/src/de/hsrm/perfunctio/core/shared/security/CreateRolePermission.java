package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to create a new role.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class CreateRolePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public CreateRolePermission() {
		super("CreateRole");
	}
}
