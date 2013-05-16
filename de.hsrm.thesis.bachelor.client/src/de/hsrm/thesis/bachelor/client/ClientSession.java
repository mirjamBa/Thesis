package de.hsrm.thesis.bachelor.client;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.client.servicetunnel.http.HttpServiceTunnel;
import org.eclipse.scout.rt.shared.services.common.code.CODES;

import de.hsrm.thesis.bachelor.client.ui.desktop.Desktop;

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

  @Override
  public void execLoadSession() throws ProcessingException {
    setServiceTunnel(new HttpServiceTunnel(this, getBundle().getBundleContext().getProperty("server.url")));

    //pre-load all known code types
    CODES.getAllCodeTypes(de.hsrm.thesis.bachelor.shared.Activator.PLUGIN_ID);

//     turn client notification polling on
    getServiceTunnel().setClientNotificationPollInterval(2000L);

    setDesktop(new Desktop());

  }

  @Override
  public void execStoreSession() throws ProcessingException {
    // disable notification polling with -1
    ClientSession.get().getServiceTunnel().setClientNotificationPollInterval(-1);
  }
}
