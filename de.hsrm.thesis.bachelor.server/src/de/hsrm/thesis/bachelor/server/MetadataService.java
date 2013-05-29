package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.IMetadataService;
import de.hsrm.thesis.bachelor.shared.MetadataFormData;

public class MetadataService extends AbstractService implements IMetadataService {

  @Override
  public MetadataFormData prepareCreate(MetadataFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public MetadataFormData create(MetadataFormData formData) throws ProcessingException {
    //TODO [mba] business logic here.
    return formData;
  }

  @Override
  public MetadataFormData load(MetadataFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public MetadataFormData store(MetadataFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public Object[][] getAttributes() throws ProcessingException {
    return SQL.select("SELECT attribute_id, "
        + "                   name, "
        + "                   datatype_id, "
        + "                   filetype_id "
        + "            FROM metadata_attribute ");
  }
}
