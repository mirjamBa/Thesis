package de.hsrm.perfunctio.administration.shared.services.common.text;

import org.eclipse.scout.rt.shared.services.common.text.AbstractDynamicNlsTextProviderService;
import org.eclipse.scout.service.IService2;

/**
 * Textservice for the administration view
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class FileAdminTextProviderService
		extends
			AbstractDynamicNlsTextProviderService implements IService2 {

	@Override
	protected String getDynamicNlsBaseName() {
		return "resources.texts.Texts";
	}
}
