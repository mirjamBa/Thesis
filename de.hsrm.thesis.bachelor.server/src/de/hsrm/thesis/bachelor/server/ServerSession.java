package de.hsrm.thesis.bachelor.server;

import java.security.AccessController;

import javax.security.auth.Subject;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.AbstractServerSession;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.shared.services.process.IUserProcessService;

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

  @SuppressWarnings("unchecked")
  @FormData
  public ICode<Integer> getPermission() {
    return getSharedContextVariable(IUserProcessService.PERMISSION_KEY, ICode.class);
  }

  @FormData
  public void setPermission(ICode<Integer> newValue) {
    setSharedContextVariable(IUserProcessService.PERMISSION_KEY, ICode.class, newValue);
  }

  @Override
  protected void execLoadSession() throws ProcessingException {
    if (getUserId() != null && Subject.getSubject(AccessController.getContext()) != Activator.getDefault().getBackendSubject()) {
      logger.info("created a new session for " + getUserId());

      setPermission(SERVICES.getService(IUserProcessService.class).getUserPermission(getUserId()));

      SERVICES.getService(IUserProcessService.class).registerUser();

    }
  }
}
