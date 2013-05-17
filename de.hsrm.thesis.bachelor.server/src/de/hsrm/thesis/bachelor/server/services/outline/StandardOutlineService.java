package de.hsrm.thesis.bachelor.server.services.outline;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.server.ServerSession;
import de.hsrm.thesis.bachelor.shared.services.outline.IStandardOutlineService;
import de.hsrm.thesis.bachelor.shared.services.process.IUserProcessService;

public class StandardOutlineService extends AbstractService implements IStandardOutlineService {

  @Override
  public String[] getOnlineUsers() throws ProcessingException {
    Set<String> allUsers = SERVICES.getService(IUserProcessService.class).getUsersOnline();
    Set<String> users = new HashSet<String>(allUsers);
    // remove myself
    users.remove(ServerSession.get().getUserId());
    return users.toArray(new String[users.size()]);
  }
}
