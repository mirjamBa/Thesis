package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.CreateFileFormat0Permission;
import de.hsrm.thesis.bachelor.shared.FileFormat0FormData;
import de.hsrm.thesis.bachelor.shared.IFileFormat0Service;
import de.hsrm.thesis.bachelor.shared.ReadFileFormat0Permission;
import de.hsrm.thesis.bachelor.shared.UpdateFileFormat0Permission;

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
    if (!ACCESS.check(new CreateFileFormat0Permission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here.
    return formData;
  }

  @Override
  public FileFormat0FormData load(FileFormat0FormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadFileFormat0Permission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public FileFormat0FormData store(FileFormat0FormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateFileFormat0Permission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }
}
