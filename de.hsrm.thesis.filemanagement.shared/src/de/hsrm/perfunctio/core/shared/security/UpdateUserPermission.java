package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to modify user
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class UpdateUserPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public UpdateUserPermission() {
		super(UpdateUserPermission.class.getName());
	}
}
