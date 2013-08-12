package de.hsrm.perfunctio.core.shared.services.common.text;

import org.eclipse.scout.rt.shared.services.common.text.AbstractDynamicNlsTextProviderService;
import org.eclipse.scout.service.IService2;

/**
 * Text Service for the perfunctio core
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class FilemanagementTextProviderService
		extends
			AbstractDynamicNlsTextProviderService implements IService2 {

	@Override
	protected String getDynamicNlsBaseName() {
		return "resources.texts.Texts";
	}
}
