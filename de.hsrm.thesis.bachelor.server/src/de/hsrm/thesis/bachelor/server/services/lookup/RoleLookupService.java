package de.hsrm.thesis.bachelor.server.services.lookup;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.server.ServerSession;
import de.hsrm.thesis.bachelor.shared.services.lookup.IRoleLookupService;
import de.hsrm.thesis.filemanagement.shared.services.IUserProcessService;

public class RoleLookupService extends AbstractSqlLookupService implements IRoleLookupService {

  @Override
  protected String getConfiguredSqlSelect() {

    //FIXME
    Long userId;
    try {

      //select only roles which are created by the logged in user [except of admin & user role]
      userId = SERVICES.getService(IUserProcessService.class).getUserId(ServerSession.get().getUserId());
      //FIXME userid should not be null
      if (userId == null) {
        return "" +
            "SELECT  R.ROLE_ID, " +
            "        R.NAME " +
            "FROM    ROLE R " +
            "WHERE   R.USER_CREATOR_ID IS NULL " +
            "<key>   AND R.ROLE_ID = :key </key> " +
            "<text>  AND UPPER(R.NAME) LIKE UPPER(:text||'%') </text> " +
            "<all> </all> ";
      }
      else {
        return "" +
            "SELECT  R.ROLE_ID, " +
            "        R.NAME " +
            "FROM    ROLE R " +
            "WHERE   R.USER_CREATOR_ID = " + userId + " OR R.USER_CREATOR_ID IS NULL " +
            "<key>   AND R.ROLE_ID = :key </key> " +
            "<text>  AND UPPER(R.NAME) LIKE UPPER(:text||'%') </text> " +
            "<all> </all> ";
      }
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
