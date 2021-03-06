package de.hsrm.perfunctio.core.shared.services.lookup;

import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

import de.hsrm.perfunctio.core.shared.services.lookup.ILanguageLookupService;

/**
 * LookupCall for available language lookup-rows
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class LanguageLookupCall extends LookupCall {

	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends ILookupService> getConfiguredService() {
		return ILanguageLookupService.class;
	}
}
