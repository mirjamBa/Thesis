package de.hsrm.thesis.bachelor.shared;

import java.io.File;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFileService extends IService2 {

  FileFormData prepareCreate(FileFormData formData) throws ProcessingException;

  FileFormData create(FileFormData formData) throws ProcessingException;

  FileFormData load(FileFormData formData) throws ProcessingException;

  FileFormData store(FileFormData formData) throws ProcessingException;

  public Object[][] getFiles() throws ProcessingException;

  public String saveFile(File file) throws ProcessingException;
}
