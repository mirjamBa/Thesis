package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFileFormat0Service extends IService2 {

  FileFormat0FormData prepareCreate(FileFormat0FormData formData) throws ProcessingException;

  FileFormat0FormData create(FileFormat0FormData formData) throws ProcessingException;

  FileFormat0FormData load(FileFormat0FormData formData) throws ProcessingException;

  FileFormat0FormData store(FileFormat0FormData formData) throws ProcessingException;
}
