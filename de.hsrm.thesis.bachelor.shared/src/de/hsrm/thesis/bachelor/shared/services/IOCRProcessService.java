package de.hsrm.thesis.bachelor.shared.services;

import org.eclipse.scout.service.IService;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.commons.exception.ProcessingException;

@InputValidation(IValidationStrategy.PROCESS.class)
  public interface IOCRProcessService extends IService{

  public void doOCR() throws ProcessingException;
}
