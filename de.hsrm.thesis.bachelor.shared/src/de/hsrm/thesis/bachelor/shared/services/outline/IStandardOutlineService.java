package de.hsrm.thesis.bachelor.shared.services.outline;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.IService;

public interface IStandardOutlineService extends IService {

  public String[] getOnlineUsers() throws ProcessingException;
}
