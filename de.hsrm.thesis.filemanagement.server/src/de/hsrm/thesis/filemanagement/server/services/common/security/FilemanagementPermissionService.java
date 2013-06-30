package de.hsrm.thesis.filemanagement.server.services.common.security;

import org.eclipse.scout.commons.annotations.Priority;
import org.eclipse.scout.rt.server.services.common.security.PermissionService;
import org.eclipse.scout.rt.shared.services.common.security.IPermissionService;
import org.osgi.framework.Bundle;

/**
 * Custom Permission Service, that accept all *Permission classes. Workaround
 * for Bug 410561 https://bugs.eclipse.org/bugs/show_bug.cgi?id=410561
 */
@Priority(100)
public class FilemanagementPermissionService extends PermissionService
		implements
			IPermissionService {

	@Override
	protected boolean acceptClassName(Bundle bundle, String className) {
		return className.indexOf("Permission") >= 0;
	}
}
