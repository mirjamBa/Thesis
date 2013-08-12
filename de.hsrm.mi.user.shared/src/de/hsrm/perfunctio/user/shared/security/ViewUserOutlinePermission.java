package de.hsrm.perfunctio.user.shared.security;

import java.security.BasicPermission;

/**
 * Authority to view the user outline
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ViewUserOutlinePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public ViewUserOutlinePermission() {
		super("ViewUserOutline");
	}
}
