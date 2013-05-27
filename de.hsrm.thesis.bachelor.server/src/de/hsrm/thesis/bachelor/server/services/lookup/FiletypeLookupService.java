package de.hsrm.thesis.bachelor.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;

import de.hsrm.thesis.bachelor.shared.services.lookup.IFiletypeLookupService;

public class FiletypeLookupService extends AbstractSqlLookupService implements IFiletypeLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT DISTINCT "
        + "     F.FILETYPE_ID, "
        + "     F.NAME "
        + " FROM FILETYPE F "
        + " WHERE 1=1 "
        + " <key> AND F.FILETYPE_ID = :key </key> "
        + " <text> AND UPPER(F.NAME) LIKE UPPER(:text||'%') </text> "
        + " <all></all> ";
  }
}
