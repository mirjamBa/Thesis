package de.hsrm.mi.storage.database.derby.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.filemanagement.shared.services.IVersionService;
import de.hsrm.thesis.filemanagement.shared.services.formdata.VersionFormData;

public class VersionService extends AbstractService implements IVersionService {

  @Override
  public VersionFormData updateFileTypeVersionControl(VersionFormData formData) throws ProcessingException {
    SQL.update("UPDATE filetype_version_control SET version_control = :version WHERE filetype_id = :filetypeId",
        new NVPair("version", formData.getVersion().getValue()),
        new NVPair("filetypeId", formData.getFileType().getValue()));
    return null;
  }

}
