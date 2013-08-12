package de.hsrm.perfunctio.core.client.ui.forms;

import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import de.hsrm.perfunctio.core.shared.services.lookup.AllRoleLookupCall;

/**
 * Listbox which contains all user created roles (RoleLookupCall)
 * 
 * @author Mirjam Bayatloo
 * 
 */
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
		return AllRoleLookupCall.class;
	}

//	@Override
//	protected void execPrepareLookup(LookupCall call)
//			throws ProcessingException {
//		((RoleLookupCall) call).setUserId(SERVICES.getService(
//				IUserProcessService.class).getCurrentUserId());
//	}

	@Override
	public String getTooltipText() {
		return TEXTS.get("TooltipRolesField");
	}
}
