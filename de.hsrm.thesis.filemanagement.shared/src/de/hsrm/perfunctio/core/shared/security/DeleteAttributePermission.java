package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority for deleting an attribute.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class DeleteAttributePermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public DeleteAttributePermission() {
		super("DeleteAttribute");
	}
}
