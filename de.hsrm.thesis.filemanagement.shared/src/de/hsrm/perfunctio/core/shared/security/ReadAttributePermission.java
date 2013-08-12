package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority for reading attributes.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ReadAttributePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public ReadAttributePermission() {
		super("ReadAttribute");
	}
}
