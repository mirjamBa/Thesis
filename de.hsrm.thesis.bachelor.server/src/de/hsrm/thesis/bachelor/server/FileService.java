package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.FileFormData;
import de.hsrm.thesis.bachelor.shared.IFileService;

public class FileService extends AbstractService implements IFileService {

  @Override
  public FileFormData prepareCreate(FileFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public FileFormData create(FileFormData formData) throws ProcessingException {
    //TODO [mba] business logic here.
    return formData;
  }

  @Override
  public FileFormData load(FileFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public FileFormData store(FileFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }
}
