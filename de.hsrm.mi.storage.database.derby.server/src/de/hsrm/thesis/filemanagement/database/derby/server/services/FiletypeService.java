package de.hsrm.thesis.filemanagement.database.derby.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.filemanagement.shared.services.IFiletypeService;

public class FiletypeService extends AbstractService implements IFiletypeService {

	@Override
	public Object[][] getFiletypes() throws ProcessingException {
		Object[][] res = SQL.select("SELECT filetype_id, version_control FROM filetype_version_control");
		return res;
	}

}
