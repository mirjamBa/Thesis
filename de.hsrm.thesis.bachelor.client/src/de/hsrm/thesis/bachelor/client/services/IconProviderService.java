package de.hsrm.thesis.bachelor.client.services;

import org.eclipse.scout.rt.client.services.common.icon.IconSpec;
import org.eclipse.scout.service.AbstractService;
import org.osgi.framework.Bundle;

import de.hsrm.thesis.bachelor.client.Activator;

public class IconProviderService extends AbstractService implements IIconProviderService {

  @Override
  public Bundle getHostBundle() {
    return Activator.getDefault().getBundle();
  }

  @Override
  public int getRanking() {
    return -56;
  }

  @Override
  public IconSpec getIconSpec(String iconName) {
    byte[] content = Activator.getDefault().getImageFromCache(iconName);
    if (content == null) {
      return null;
    }

    return new IconSpec(content);
  }
}
