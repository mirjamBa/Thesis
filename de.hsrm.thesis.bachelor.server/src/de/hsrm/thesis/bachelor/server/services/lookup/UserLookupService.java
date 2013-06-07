package de.hsrm.thesis.bachelor.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;

import de.hsrm.thesis.bachelor.shared.services.lookup.IUserLookupService;

public class UserLookupService extends AbstractSqlLookupService implements IUserLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return "SELECT DISTINCT "
        + "     U_ID, "
        + "     USERNAME "
        + " FROM TABUSERS "
        + " WHERE 1=1 "
        + " <key> AND U_ID = :key </key> "
        + " <text> AND UPPER(USERNAME) LIKE UPPER(:text||'%') </text> "
        + " <all></all> ";
  }
}
