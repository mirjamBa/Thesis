package de.hsrm.thesis.bachelor.server;

import java.security.AccessController;

import javax.security.auth.Subject;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.AbstractServerSession;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.shared.services.IStartupService;

public class ServerSession extends AbstractServerSession {
  private static IScoutLogger logger = ScoutLogManager.getLogger(ServerSession.class);

  public ServerSession() {
    super(true);
  }

  /**
   * @return session in current ThreadContext
   */
  public static ServerSession get() {
    return ServerJob.getCurrentSession(ServerSession.class);
  }

  @Override
  public <T> void setSharedContextVariable(String name, Class<T> type, T value) {
    super.setSharedContextVariable(name, type, value);
  }

  @Override
  protected void execLoadSession() throws ProcessingException {
    if (getUserId() != null && Subject.getSubject(AccessController.getContext()) != Activator.getDefault().getBackendSubject()) {
      SERVICES.getService(IStartupService.class).initSessionData();
      //using anonymous security filter: set global role for all users here instead of line 48f.
//      ServerJob.getCurrentSession().setData(IStartupService.USER_NR, SERVICES.getService(IRoleProcessService.class).getAdminRoleId());

    }
  }
}
