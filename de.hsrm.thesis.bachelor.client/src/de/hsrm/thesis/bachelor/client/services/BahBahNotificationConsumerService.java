package de.hsrm.thesis.bachelor.client.services;

import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ClientAsyncJob;
import org.eclipse.scout.rt.client.ClientSyncJob;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.services.common.clientnotification.ClientNotificationConsumerEvent;
import org.eclipse.scout.rt.shared.services.common.clientnotification.IClientNotification;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.client.ui.desktop.Desktop;
import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages.UserNodePage;
import de.hsrm.thesis.bachelor.client.ui.forms.ChatForm;
import de.hsrm.thesis.bachelor.shared.notification.MessageNotification;
import de.hsrm.thesis.bachelor.shared.notification.RefreshBuddiesNotification;

public class BahBahNotificationConsumerService extends AbstractService implements IBahBahNotificationConsumerService {

  private static IScoutLogger logger = ScoutLogManager.getLogger(BahBahNotificationConsumerService.class);

  private void handleMessage(MessageNotification notification) {
    UserNodePage userPage = getUserNodePage();
    String buddy = notification.getSenderName();

    if (userPage != null) {
      try {
        ChatForm form = userPage.getChatForm(buddy);
        if (form != null) {
          form.getHistoryField().addMessage(false, buddy, form.getUserName(), new Date(), notification.getMessage());
        }
      }
      catch (Throwable t) {
        logger.error("handling of remote message failed.", t);
      }
    }
  }

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
              handleRefreshBuddies();
            }
          }.schedule();
        }
      }.schedule();
    }
    else if (notification instanceof MessageNotification) {
      new ClientAsyncJob("async wrapper (message)", session) {
        @Override
        protected void runVoid(IProgressMonitor monitor) throws Throwable {
          new ClientSyncJob("message", session) {
            @Override
            protected void runVoid(IProgressMonitor monitor1) throws Throwable {
              handleMessage((MessageNotification) notification);
            }
          }.schedule();
        }
      }.schedule();
    }
  }

  private UserNodePage getUserNodePage() {
    if (Desktop.get() == null) {
      return null;
    }
    else {
      return Desktop.get().getUserNodePage();
    }
  }

  private void handleRefreshBuddies() {
    UserNodePage userPage = getUserNodePage();

    if (userPage != null) {
      try {
        logger.info("refreshing buddies on client");
        userPage.updateBuddyPages();
      }
      catch (Throwable t) {
        logger.error("handling of remote message failed.", t);
      }
    }
  }
}
