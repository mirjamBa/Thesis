package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
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

  @Override
  public Object[][] getFiles() throws ProcessingException {
    SQL.select("SELECT file_id,"
        + "            filetype_id,"
        + "            number,"
        + "            XX Title,"
        + "            XX Author,"
        + "            u_id,"
        + "            XX Filesize,"
        + "            XX Format,"
        + "            XX Mime");

    return null;
  }
}
