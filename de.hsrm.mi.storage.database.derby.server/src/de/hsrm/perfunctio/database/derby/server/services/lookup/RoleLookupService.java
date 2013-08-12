package de.hsrm.perfunctio.database.derby.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;

import de.hsrm.perfunctio.core.shared.services.lookup.IRoleLookupService;

/**
 * Fetches all role-ids and rolename as lookup-rows from the database
 * 
 * @author Mirjam Bayatloo
 * 
 */
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
