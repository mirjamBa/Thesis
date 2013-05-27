package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.FileFormat0FormData;
import de.hsrm.thesis.bachelor.shared.IFileFormat0Service;

public class FileFormat0Service extends AbstractService implements IFileFormat0Service {

  @Override
  public FileFormat0FormData prepareCreate(FileFormat0FormData formData) throws ProcessingException {
//    if (!ACCESS.check(new CreateFileFormat0Permission())) {
//      throw new VetoException(TEXTS.get("AuthorizationFailed"));
//    }
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public FileFormat0FormData create(FileFormat0FormData formData) throws ProcessingException {
    //TODO [mba] business logic here.
    return formData;
  }

  @Override
  public FileFormat0FormData load(FileFormat0FormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public FileFormat0FormData store(FileFormat0FormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }
}
