package de.hsrm.thesis.filemanagement.shared.services.common.text;

import org.eclipse.scout.rt.shared.services.common.text.AbstractDynamicNlsTextProviderService;
import org.eclipse.scout.service.IService2;

public class FilemanagementTextProviderService extends AbstractDynamicNlsTextProviderService implements IService2 {

	@Override
	protected String getDynamicNlsBaseName() {
		return "resources.texts.Texts";
	}
}