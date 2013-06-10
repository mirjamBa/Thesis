package de.hsrm.thesis.bachelor.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;

import de.hsrm.thesis.bachelor.shared.services.lookup.IFiletypeForFileformatsLookupService;

public class FiletypeForFileformatsLookupService extends AbstractSqlLookupService implements IFiletypeForFileformatsLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT      f1.filetype_id,"
        + "             f1.name  "
        + " FROM        filetype f1, "
        + "             fileformat f2 "
        + " WHERE       UPPER(f2.format) = UPPER(:fileformat) "
        + " AND         f2.filetype_id = f1.filetype_id "
        + "<key> AND    f1.filetype_id = :key </key> "
        + "<text>AND    UPPER(f1.name) LIKE UPPER(:text || '%') </text> "
        + "<all>        </all> ";
  }
}
