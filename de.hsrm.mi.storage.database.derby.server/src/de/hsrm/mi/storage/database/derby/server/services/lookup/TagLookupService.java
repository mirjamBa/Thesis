package de.hsrm.mi.storage.database.derby.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;

import de.hsrm.thesis.filemanagement.shared.services.lookup.ITagLookupService;

public class TagLookupService extends AbstractSqlLookupService implements ITagLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT DISTINCT "
        + "     TAG_ID, "
        + "     NAME "
        + " FROM TAG "
        + " WHERE 1=1 "
        + " <key> AND TAG_ID = :key </key> "
        + " <text> AND UPPER(NAME) LIKE UPPER(:text||'%') </text> "
        + " <all></all> ";
  }
}
