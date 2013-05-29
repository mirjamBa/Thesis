package de.hsrm.thesis.bachelor.client.services;

import org.eclipse.scout.rt.client.services.common.icon.IconSpec;
import org.eclipse.scout.service.IService;
import org.osgi.framework.Bundle;

public interface IIconProviderService extends IService {

  /**
   * @return
   */
  Bundle getHostBundle();

  /**
   * @return
   */
  int getRanking();

  /**
   * @param iconName
   * @return
   */
  IconSpec getIconSpec(String iconName);
}
