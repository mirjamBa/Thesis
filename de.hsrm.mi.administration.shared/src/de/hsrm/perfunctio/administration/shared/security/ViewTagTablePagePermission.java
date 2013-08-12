package de.hsrm.perfunctio.administration.shared.security;

import java.security.BasicPermission;

/**
 * Authority to view the tag table page
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ViewTagTablePagePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public ViewTagTablePagePermission() {
		super("TagTablePage");
	}
}
