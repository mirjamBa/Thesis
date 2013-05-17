package de.hsrm.thesis.bachelor.shared.services.process;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

@InputValidation(IValidationStrategy.NO_CHECK.class)
public interface INotificationProcessService extends IService {
  public void sendRefreshBuddies() throws ProcessingException;

  public void sendMessage(String buddyName, String message) throws ProcessingException;
}
