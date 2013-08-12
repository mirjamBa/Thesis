package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to modify roles
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class UpdateRolePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public UpdateRolePermission() {
		super("UpdateRole");
	}
}
