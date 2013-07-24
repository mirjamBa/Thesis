package de.hsrm.perfunctio.core.client.ui.forms;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.services.IUserProcessService;
import de.hsrm.perfunctio.core.shared.services.lookup.RoleLookupCall;

public class RolesField extends AbstractListBox<Long> {

	@Override
	protected int getConfiguredGridH() {
		return 5;
	}

	@Override
	protected String getConfiguredLabel() {
		return TEXTS.get("UserRoles");
	}

	@Override
	protected Class<? extends LookupCall> getConfiguredLookupCall() {
		return RoleLookupCall.class;
	}

	@Override
	protected void execPrepareLookup(LookupCall call)
			throws ProcessingException {
		((RoleLookupCall) call).setUserId(SERVICES.getService(
				IUserProcessService.class).getCurrentUserId());
	}

	@Override
	public String getTooltipText() {
		return TEXTS.get("TooltipRolesField");
	}
}
