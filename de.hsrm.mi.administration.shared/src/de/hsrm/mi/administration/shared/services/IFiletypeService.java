package de.hsrm.mi.administration.shared.services;

import javax.activation.MimeType;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFiletypeService extends IService {

  public Object[][] getFiletypes() throws ProcessingException;
  
  public Long getFiletypeId(MimeType mimeType) throws ProcessingException;

}
