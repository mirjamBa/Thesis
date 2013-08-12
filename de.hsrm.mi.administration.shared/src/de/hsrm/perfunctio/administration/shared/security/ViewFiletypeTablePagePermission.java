package de.hsrm.perfunctio.administration.shared.security;

import java.security.BasicPermission;

/**
 * Authority to view the filetype table page
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ViewFiletypeTablePagePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public ViewFiletypeTablePagePermission() {
		super("FiletypeTablePage");
	}
}
