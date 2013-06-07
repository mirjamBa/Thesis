package de.hsrm.thesis.bachelor.server.services;

import org.eclipse.scout.commons.annotations.Priority;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.server.ServerSession;
import de.hsrm.thesis.bachelor.shared.services.ISharedContextService;

@Priority(-1)
public class SharedContextService extends AbstractService implements ISharedContextService {

  @Override
  public <T> void setSharedContextVariable(String name, Class<T> type, T value) throws ProcessingException {
    ServerSession.get().setSharedContextVariable(name, type, value);
  }
}
