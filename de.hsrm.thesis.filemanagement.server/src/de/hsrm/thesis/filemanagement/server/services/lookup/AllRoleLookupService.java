package de.hsrm.thesis.filemanagement.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;
import de.hsrm.thesis.filemanagement.shared.services.lookup.IAllRoleLookupService;

public class AllRoleLookupService extends AbstractSqlLookupService implements IAllRoleLookupService{

	@Override
	protected String getConfiguredSqlSelect(){
	  return " "
	  		+ "SELECT	ROLE_ID, "
	  		+ "			NAME "
	  		+ "FROM 	ROLE "
	  		+ "WHERE 	1 = 1 "
	  		+ "<key>	AND ROLE_ID = :key </key> "
	  		+ "<text>	AND UPPER(NAME) LIKE UPPER(:text||'%') </text> "
	  		+ "<all>	</all> "; 
	}
}
