package de.hsrm.mi.user.shared.services.common.text;

import org.eclipse.scout.rt.shared.services.common.text.AbstractDynamicNlsTextProviderService;
import org.eclipse.scout.service.IService2;

public class UserTextProviderService
		extends
			AbstractDynamicNlsTextProviderService implements IService2 {

	@Override
	protected String getDynamicNlsBaseName() {
		return "resources.texts.Texts";
	}
}
