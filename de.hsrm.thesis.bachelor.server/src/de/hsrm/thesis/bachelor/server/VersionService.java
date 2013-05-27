package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.IVersionService;
import de.hsrm.thesis.bachelor.shared.VersionFormData;

public class VersionService extends AbstractService implements IVersionService {

  @Override
  public VersionFormData load(VersionFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public VersionFormData store(VersionFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public Object[][] getVersionControlOfFiletypes() throws ProcessingException {
    return SQL.select("SELECT FILETYPE_ID, "
        + "                   NAME, "
        + "                   VERSION_CONTROL "
        + "                   FROM FILETYPE ");
  }
}
