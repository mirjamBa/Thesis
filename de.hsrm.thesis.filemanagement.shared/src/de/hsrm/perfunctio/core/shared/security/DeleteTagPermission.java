package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

public class DeleteTagPermission extends BasicPermission{

	private static final long serialVersionUID = 0L;

	public DeleteTagPermission() {
	super("DeleteTag");
	}
}