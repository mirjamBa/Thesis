package de.hsrm.thesis.bachelor.shared.services;

import org.eclipse.scout.service.IService;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.commons.exception.ProcessingException;

@InputValidation(IValidationStrategy.PROCESS.class)
  public interface ILogoProcessService extends IService{

  public Object[][] getLogoTableData() throws ProcessingException;

  public byte[] getLogo(String imageId) throws ProcessingException;
}
