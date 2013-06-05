package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFileFormatService extends IService2 {

  FileFormatFormData create(FileFormatFormData formData) throws ProcessingException;

  void delete(Long[] ids) throws ProcessingException;

  FileFormatFormData update(FileFormatFormData formData) throws ProcessingException;

  public Object[][] getFileFormats(Long filetypeNr) throws ProcessingException;
}
