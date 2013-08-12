package de.hsrm.perfunctio.core.shared.services.lookup;

import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import de.hsrm.perfunctio.core.shared.services.lookup.IPermissionsLookupService;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

/**
 * LookupCall for all available permission lookup-rows.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class PermissionsLookupCall extends LookupCall {

	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends ILookupService> getConfiguredService() {
		return IPermissionsLookupService.class;
	}
}
