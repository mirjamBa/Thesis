package de.hsrm.perfunctio.administration.shared.security;

import java.security.BasicPermission;

/**
 * Authority to view the ShowInFileTableTablePage
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ViewShowInFileTablePagePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public ViewShowInFileTablePagePermission() {
		super("ShowInFileTablePage");
	}
}
