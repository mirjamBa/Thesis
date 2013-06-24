package de.hsrm.thesis.filemanagement.shared.services;

import java.io.File;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface ITikaService extends IService2 {

  public Map<String, String> extractDataFromFile(File file) throws ProcessingException;
}
