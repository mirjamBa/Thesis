package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority for deleting a tag.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class DeleteTagPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public DeleteTagPermission() {
		super("DeleteTag");
	}
}
