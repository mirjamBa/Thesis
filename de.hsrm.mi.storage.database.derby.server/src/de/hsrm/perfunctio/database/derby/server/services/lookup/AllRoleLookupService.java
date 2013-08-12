package de.hsrm.perfunctio.database.derby.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;

import de.hsrm.perfunctio.core.shared.services.lookup.IAllRoleLookupService;

/**
 * Fetches all roles as lookup-rows from the database
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class AllRoleLookupService extends AbstractSqlLookupService
		implements
			IAllRoleLookupService {

	@Override
	protected String getConfiguredSqlSelect() {
		return " " + "SELECT	ROLE_ID, " + "			NAME " + "FROM 	ROLE "
				+ "WHERE 	1 = 1 " + "<key>	AND ROLE_ID = :key </key> "
				+ "<text>	AND UPPER(NAME) LIKE UPPER(:text||'%') </text> "
				+ "<all>	</all> ";
	}
}
