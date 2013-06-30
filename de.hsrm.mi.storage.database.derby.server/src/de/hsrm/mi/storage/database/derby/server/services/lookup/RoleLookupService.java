package de.hsrm.mi.storage.database.derby.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;

import de.hsrm.thesis.filemanagement.shared.services.lookup.IRoleLookupService;

public class RoleLookupService extends AbstractSqlLookupService
		implements
			IRoleLookupService {

	@Override
	protected String getConfiguredSqlSelect() {

		return ""
				+ "SELECT  R.ROLE_ID, "
				+ "        R.NAME "
				+ "FROM    ROLE R "
				+ "WHERE   R.USER_CREATOR_ID = :userId OR R.USER_CREATOR_ID IS NULL "
				+ "<key>   AND R.ROLE_ID = :key </key> "
				+ "<text>  AND UPPER(R.NAME) LIKE UPPER(:text||'%') </text> "
				+ "<all> </all> ";

	}
}
