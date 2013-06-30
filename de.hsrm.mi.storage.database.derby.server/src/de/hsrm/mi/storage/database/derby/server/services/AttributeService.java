package de.hsrm.mi.storage.database.derby.server.services;

import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.filemanagement.shared.security.ReadAttributePermission;
import de.hsrm.thesis.filemanagement.shared.services.IAttributeService;
import de.hsrm.thesis.filemanagement.shared.utility.ArrayUtility;

public class AttributeService extends AbstractService
		implements
			IAttributeService {

	@Override
	public Object[][] getAllAttributes() throws ProcessingException {
		if(!ACCESS.check(new ReadAttributePermission())){
			  throw new VetoException(TEXTS.get("VETOReadAttributePermission"));
		  }
		return SQL.select("SELECT attribute_id, " 
				+ "			name, "
				+ "			show_in_table, " 
				+ "			datatype, " 
				+ "			filetype_id "
				+ "	FROM 	metadata_attribute");
	}

	@Override
	public Map<Object, Object> getDisplayedAttributeNamesAndDatatype()
			throws ProcessingException {
		if(!ACCESS.check(new ReadAttributePermission())){
			  throw new VetoException(TEXTS.get("VETOReadAttributePermission"));
		  }
		Object[][] data = SQL
				.select("SELECT name, datatype FROM metadata_attribute WHERE show_in_table=true");

		return ArrayUtility.getDictionary(data, 0, 1);

	}

	@Override
	public Object[][] getAttributeData(String attributenName)
			throws ProcessingException {
		if(!ACCESS.check(new ReadAttributePermission())){
			  throw new VetoException(TEXTS.get("VETOReadAttributePermission"));
		  }
		return SQL.select("SELECT attribute_id, "
				+ "					name, "
				+ "					datatype " 
				+ "			FROM metadata_attribute "
				+ "			WHERE UPPER(name) LIKE UPPER(:attributeName) ",
				new NVPair("attributeName", attributenName));
	}

}
