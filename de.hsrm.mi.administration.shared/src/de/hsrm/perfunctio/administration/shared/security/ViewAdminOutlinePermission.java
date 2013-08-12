package de.hsrm.perfunctio.administration.shared.security;

import java.security.BasicPermission;

/**
 * Authority to view the filemanagement administration outline
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ViewAdminOutlinePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public ViewAdminOutlinePermission() {
		super("AdminOutline");
	}
}
