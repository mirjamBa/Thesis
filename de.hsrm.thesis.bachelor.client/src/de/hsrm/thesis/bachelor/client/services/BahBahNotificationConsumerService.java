package de.hsrm.thesis.bachelor.client.services;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ClientAsyncJob;
import org.eclipse.scout.rt.client.ClientSyncJob;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.services.common.clientnotification.ClientNotificationConsumerEvent;
import org.eclipse.scout.rt.shared.services.common.clientnotification.IClientNotification;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.notification.RefreshBuddiesNotification;

public class BahBahNotificationConsumerService extends AbstractService implements IBahBahNotificationConsumerService {

  private static IScoutLogger logger = ScoutLogManager.getLogger(BahBahNotificationConsumerService.class);

  @Override
  public void handleEvent(ClientNotificationConsumerEvent e, boolean sync) {
    logger.info("received client notification event for user '" + ClientSyncJob.getCurrentSession().getUserId() + "'");

    final IClientNotification notification = e.getClientNotification();
    final IClientSession session = ClientSyncJob.getCurrentSession();

    // deal with notification in async jobs to prevent blocking of the model thread
    if (notification instanceof RefreshBuddiesNotification) {
      new ClientAsyncJob("async wrapper (refresh buddies)", session) {
        @Override
        protected void runVoid(IProgressMonitor monitor) throws Throwable {
          new ClientSyncJob("refresh buddies", session) {
            @Override
            protected void runVoid(IProgressMonitor monitor1) throws Throwable {
//              handleRefreshBuddies();
            }
          }.schedule();
        }
      }.schedule();
    }
  }

}
