package de.hsrm.perfunctio.core.shared.services.lookup;

import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import de.hsrm.perfunctio.core.shared.services.lookup.IAllRoleLookupService;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

public class AllRoleLookupCall extends LookupCall{

	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends ILookupService> getConfiguredService() {
	  return IAllRoleLookupService.class;
	}
}
