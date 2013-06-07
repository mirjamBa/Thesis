package de.hsrm.thesis.bachelor.shared.services;

import java.io.File;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface ITikaService extends IService2 {

  public void extractDataFromFile(File file) throws ProcessingException;
}
