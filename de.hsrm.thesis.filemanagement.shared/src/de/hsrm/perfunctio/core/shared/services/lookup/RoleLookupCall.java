package de.hsrm.perfunctio.core.shared.services.lookup;

import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

import de.hsrm.perfunctio.core.shared.services.lookup.IRoleLookupService;

import org.eclipse.scout.commons.annotations.FormData;

/**
 * LookupCall for all role lookup-rows, which have benn created by the assigned
 * user.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class RoleLookupCall extends LookupCall {

	private static final long serialVersionUID = 1L;
	private long m_userId;

	@Override
	protected Class<? extends ILookupService> getConfiguredService() {
		return IRoleLookupService.class;
	}

	@FormData
	public long getUserId() {
		return m_userId;
	}

	@FormData
	public void setUserId(long userId) {
		m_userId = userId;
	}
}
