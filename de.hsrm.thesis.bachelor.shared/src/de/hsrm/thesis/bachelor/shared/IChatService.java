package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IChatService extends IService2 {

  ChatFormData prepareCreate(ChatFormData formData) throws ProcessingException;

  ChatFormData create(ChatFormData formData) throws ProcessingException;

  ChatFormData load(ChatFormData formData) throws ProcessingException;

  ChatFormData store(ChatFormData formData) throws ProcessingException;
}
