package de.hsrm.thesis.bachelor.server.services.lookup;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;

import de.hsrm.thesis.bachelor.server.ServerSession;
import de.hsrm.thesis.bachelor.server.util.UserUtility;
import de.hsrm.thesis.bachelor.shared.services.lookup.IRoleLookupService;

public class RoleLookupService extends AbstractSqlLookupService implements IRoleLookupService {

  @Override
  protected String getConfiguredSqlSelect() {

    Long userId;
    try {

      //select only roles which are created by the logged in user [except of admin & user role]
      userId = UserUtility.getUserId(ServerSession.get().getUserId());
      return "" +
          "SELECT  R.ROLE_ID, " +
          "        R.NAME " +
          "FROM    ROLE R " +
          "WHERE   R.USER_CREATOR_ID = " + userId + " OR R.USER_CREATOR_ID IS NULL " +
          "<key>   AND R.ROLE_ID = :key </key> " +
          "<text>  AND UPPER(R.NAME) LIKE UPPER(:text||'%') </text> " +
          "<all> </all> ";
    }
    catch (ProcessingException e) {
      return "" +
          "SELECT  R.ROLE_ID, " +
          "        R.NAME " +
          "FROM    ROLE R " +
          "WHERE   1=1 " +
          "<key>   AND R.ROLE_ID = :key </key> " +
          "<text>  AND UPPER(R.NAME) LIKE UPPER(:text||'%') </text> " +
          "<all> </all> ";
    }

  }
}
