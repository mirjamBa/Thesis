package de.hsrm.thesis.filemanagement.shared.services.lookup;

import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import de.hsrm.thesis.filemanagement.shared.services.lookup.IPermissionsLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

public class PermissionsLookupCall extends LookupCall{

	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends ILookupService> getConfiguredService() {
	  return IPermissionsLookupService.class;
	}
}
