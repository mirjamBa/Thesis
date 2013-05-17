package de.hsrm.thesis.bachelor.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.services.common.clientnotification.AllUserFilter;
import org.eclipse.scout.rt.server.services.common.clientnotification.IClientNotificationService;
import org.eclipse.scout.rt.server.services.common.clientnotification.SingleUserFilter;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.server.ServerSession;
import de.hsrm.thesis.bachelor.shared.notification.MessageNotification;
import de.hsrm.thesis.bachelor.shared.notification.RefreshBuddiesNotification;
import de.hsrm.thesis.bachelor.shared.security.CreateNotificationPermission;
import de.hsrm.thesis.bachelor.shared.services.process.INotificationProcessService;

public class NotificationProcessService extends AbstractService implements INotificationProcessService {
  private static IScoutLogger logger = ScoutLogManager.getLogger(NotificationProcessService.class);

  private final static long TIMEOUT = 1000 * 60 * 10; // 10min

  @Override
  public void sendRefreshBuddies() throws ProcessingException {
    if (!ACCESS.check(new CreateNotificationPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    logger.info("queue 'update buddies' notification on server");
    IClientNotificationService service = SERVICES.getService(IClientNotificationService.class);
    service.putNotification(new RefreshBuddiesNotification(), new AllUserFilter(TIMEOUT));
  }

  @Override
  public void sendMessage(String buddyName, String message) throws ProcessingException {
    if (!ACCESS.check(new CreateNotificationPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    IClientNotificationService service = SERVICES.getService(IClientNotificationService.class);
    service.putNotification(new MessageNotification(ServerSession.get().getUserId(), message), new SingleUserFilter(buddyName, TIMEOUT));
  }

}
