package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority for removing a permission-role linkage
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class RemoveAssignToRolePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public RemoveAssignToRolePermission() {
		super("RemoveAssignToRole");
	}
}
