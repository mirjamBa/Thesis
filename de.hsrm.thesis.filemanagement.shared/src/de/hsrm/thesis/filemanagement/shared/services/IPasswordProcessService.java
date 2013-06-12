package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.service.IService;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;

@InputValidation(IValidationStrategy.PROCESS.class)
  public interface IPasswordProcessService extends IService{
}
