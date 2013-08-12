package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to create a new tag.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class CreateTagPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public CreateTagPermission() {
		super("CreateTag");
	}
}
