package de.hsrm.thesis.bachelor.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFiletypeService extends IService {

  public void organizeFiletypes() throws ProcessingException;

  public void initFiletypeXML() throws ProcessingException;

}
