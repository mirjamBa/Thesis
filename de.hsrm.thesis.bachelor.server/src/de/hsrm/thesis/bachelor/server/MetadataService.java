package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.IMetadataService;
import de.hsrm.thesis.bachelor.shared.MetadataFormData;

public class MetadataService extends AbstractService implements IMetadataService {

  @Override
  public MetadataFormData create(MetadataFormData formData) throws ProcessingException {
    SQL.insert("INSERT INTO metadata_attribute (name, datatype, filetype_id) VALUES(:name, :datatype, :filetype)",
        new NVPair("name", formData.getBezeichnung()),
        new NVPair("datatype", formData.getDatatype().getValue()),
        new NVPair("filetype", formData.getFileType().getValue()));

    return formData;
  }

  @Override
  public MetadataFormData delete(MetadataFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public MetadataFormData update(MetadataFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public Object[][] getAttributes(Long filetypeId) throws ProcessingException {
    if (filetypeId == null) {
      return SQL.select("SELECT attribute_id, "
          + "                   name, "
          + "                   datatype, "
          + "                   filetype_id "
          + "            FROM metadata_attribute ");
    }
    else {
      return SQL.select("SELECT attribute_id, "
          + "                   name, "
          + "                   datatype, "
          + "                   filetype_id "
          + "            FROM metadata_attribute "
          + "            WHERE filetype_id = :filetypeId ",
          new NVPair("filetypeId", filetypeId));
    }
  }
}
