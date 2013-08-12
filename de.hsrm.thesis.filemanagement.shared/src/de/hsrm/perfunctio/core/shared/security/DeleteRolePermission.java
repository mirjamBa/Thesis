package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority for deleting a role.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class DeleteRolePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public DeleteRolePermission() {
		super("DeleteRole");
	}
}
