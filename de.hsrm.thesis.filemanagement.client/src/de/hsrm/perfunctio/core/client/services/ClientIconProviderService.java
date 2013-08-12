package de.hsrm.perfunctio.core.client.services;

import org.eclipse.scout.rt.client.services.common.icon.IconProviderService;
import org.eclipse.scout.rt.client.services.common.icon.IconSpec;

import de.hsrm.perfunctio.core.client.Activator;

/**
 * Custom Icon provider service accessing icon cache (for icons)
 * 
 * @see <a
 *      href="http://wiki.eclipse.org/Scout/HowTo/3.8/Add_an_icon">http://wiki.eclipse.org/Scout/HowTo/3.8/Add_an_icon</a>
 * @author BSI Business Systems Integration AG
 * 
 */
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
