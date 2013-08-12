package de.hsrm.perfunctio.database.derby.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;

import de.hsrm.perfunctio.core.shared.services.lookup.IUserLookupService;

/**
 * Fetches all user_ids and usernames as lookup-rows from the database
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class UserLookupService extends AbstractSqlLookupService
		implements
			IUserLookupService {

	@Override
	protected String getConfiguredSqlSelect() {
		return "SELECT DISTINCT " + "     U_ID, " + "     USERNAME "
				+ " FROM TABUSERS " + " WHERE 1=1 "
				+ " <key> AND U_ID = :key </key> "
				+ " <text> AND UPPER(USERNAME) LIKE UPPER(:text||'%') </text> "
				+ " <all></all> ";
	}
}
