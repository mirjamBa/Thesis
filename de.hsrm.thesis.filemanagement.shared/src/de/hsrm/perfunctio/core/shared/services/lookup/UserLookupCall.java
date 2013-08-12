package de.hsrm.perfunctio.core.shared.services.lookup;

import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

import de.hsrm.perfunctio.core.shared.services.lookup.IUserLookupService;

/**
 * LookupCall for all user lookup-rows
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class UserLookupCall extends LookupCall {

	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends ILookupService> getConfiguredService() {
		return IUserLookupService.class;
	}
}
