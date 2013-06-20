package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import de.hsrm.thesis.filemanagement.shared.services.formdata.FileFormatFormData;


@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFileFormatService extends IService2 {

  FileFormatFormData create(FileFormatFormData formData) throws ProcessingException;

  void delete(Long[] ids) throws ProcessingException;

  FileFormatFormData update(FileFormatFormData formData) throws ProcessingException;

  public Object[][] getFileFormats(Long filetypeNr) throws ProcessingException;

public boolean isFormatMultipleAssigned(String fileformat) throws ProcessingException;

public Long getFiletypeForFileFormat(String fileformat) throws ProcessingException;

public boolean isFileformatRegistered(String fileformat) throws ProcessingException;

public Long[] getFiletypesForFileFormat(String fileformat) throws ProcessingException;
}
