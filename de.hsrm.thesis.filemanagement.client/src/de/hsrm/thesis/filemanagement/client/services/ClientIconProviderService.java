package de.hsrm.thesis.filemanagement.client.services;

import org.eclipse.scout.rt.client.services.common.icon.IconProviderService;
import org.eclipse.scout.rt.client.services.common.icon.IconSpec;

import de.hsrm.thesis.filemanagement.client.Activator;

public class ClientIconProviderService extends IconProviderService {
	public ClientIconProviderService() {
		setHostBundle(Activator.getDefault().getBundle());
	}

	@Override
	public int getRanking() {
		return -20;
	}

	@Override
	public IconSpec getIconSpec(String iconName) {
		IconSpec result = super.getIconSpec(iconName);
		return result;
	}
}
