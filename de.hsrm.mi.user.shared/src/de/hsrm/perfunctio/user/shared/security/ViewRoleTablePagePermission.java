package de.hsrm.perfunctio.user.shared.security;

import java.security.BasicPermission;

/**
 * Authority to view the role table page
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ViewRoleTablePagePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public ViewRoleTablePagePermission() {
		super("ViewRoleTablePage");
	}
}
