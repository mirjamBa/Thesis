package de.hsrm.thesis.filemanagement.shared.services;

import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IAttributeService extends IService {

	/**
	 * Fetches all attributes (standard and user defined from storage)
	 * [attribute id, name, display in table, data type]
	 * 
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getAllAttributes() throws ProcessingException;

	/**
	 * Fetches all attribute names and their data types which should be shown in the file table
	 * 
	 * @return String[]
	 * @throws ProcessingException
	 */
	public Map<Object, Object> getDisplayedAttributeNamesAndDatatype()
			throws ProcessingException;
	
	/**
	 * Fetches id, name and data type for an assigned attribute name
	 * @param attributenName String
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getAttributeData(String attributenName) throws ProcessingException;
}
