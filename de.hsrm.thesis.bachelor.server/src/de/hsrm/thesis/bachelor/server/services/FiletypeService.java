package de.hsrm.thesis.bachelor.server.services;

import javax.activation.MimeType;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.mi.administration.shared.services.IFiletypeService;

public class FiletypeService extends AbstractService implements IFiletypeService {

  @Override
  public Object[][] getFiletypes() throws ProcessingException {
    Object[][] res = SQL.select("SELECT filetype_id, version_control FROM filetype_version_control");
    return res;
  }

  @Override
  public Long getFiletypeId(MimeType mimeType) throws ProcessingException {
    String mimetype = mimeType.getPrimaryType();
    System.out.println("MIMETYPE: " + mimetype);
    LongHolder filetype = new LongHolder();
    SQL.selectInto("SELECT filetype_id FROM filetype WHERE name = :mimetype INTO :filetype",
        new NVPair("mimetype", mimetype),
        new NVPair("filetype", filetype));

    return filetype.getValue();
  }
}
