package de.hsrm.thesis.bachelor.client;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.client.servicetunnel.http.HttpServiceTunnel;
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.rt.shared.services.common.code.ICode;

import de.hsrm.thesis.bachelor.client.ui.desktop.Desktop;
import de.hsrm.thesis.filemanagement.shared.services.IUserProcessService;

public class ClientSession extends AbstractClientSession {
  private static IScoutLogger logger = ScoutLogManager.getLogger(ClientSession.class);

  public ClientSession() {
    super(true);
  }

  /**
   * @return session in current ThreadContext
   */
  public static ClientSession get() {
    return ClientJob.getCurrentSession(ClientSession.class);
  }

  @SuppressWarnings("unchecked")
  @FormData
  public ICode<Integer> getPermission() {
    return getSharedContextVariable(IUserProcessService.PERMISSION_KEY, ICode.class);
  }

  @Override
  public void execLoadSession() throws ProcessingException {
    setServiceTunnel(new HttpServiceTunnel(this, getBundle().getBundleContext().getProperty("server.url")));

    //pre-load all known code types
    CODES.getAllCodeTypes(de.hsrm.thesis.bachelor.shared.Activator.PLUGIN_ID);

    // turn client notification polling on
    getServiceTunnel().setClientNotificationPollInterval(1000L);

    setDesktop(new Desktop());
  }

  @Override
  public void execStoreSession() throws ProcessingException {
    // disable notification polling with -1
    ClientSession.get().getServiceTunnel().setClientNotificationPollInterval(-1);
  }

}
