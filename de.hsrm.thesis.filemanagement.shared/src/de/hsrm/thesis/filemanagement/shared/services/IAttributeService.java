package de.hsrm.thesis.filemanagement.shared.services;

import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

import de.hsrm.thesis.filemanagement.shared.services.formdata.AttributeFormData;
/**
 * Service Interface for attribute handling
 * 
 * @author Mirjam Bayatloo
 * 
 */
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
	 * Fetches all attribute names(key) and their data types (value) which
	 * should be shown in the file table
	 * 
	 * @return Map<Object, Object>
	 * @throws ProcessingException
	 */
	public Map<Object, Object> getDisplayedAttributeNamesAndDatatype()
			throws ProcessingException;

	/**
	 * Fetches id, name and data type for an assigned attribute name
	 * 
	 * @param attributenName
	 *            String
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getAttributeData(String attributenName)
			throws ProcessingException;
	
	/**
	 * Saves new meta data attribute into storage
	 * 
	 * @param formData
	 *            MetadataFormData
	 * @return MetadataFormData
	 * @throws ProcessingException
	 */
	public AttributeFormData create(AttributeFormData formData) throws ProcessingException;

	/**
	 * Deletes all meta data attributes from storage with one of the assigned
	 * ids
	 * 
	 * @param ids
	 *            Long[]
	 * @throws ProcessingException
	 */
	public void delete(Long[] ids) throws ProcessingException;

	/**
	 * Modifies the meta data attribute
	 * 
	 * @param formData
	 * @return
	 * @throws ProcessingException
	 */
	public AttributeFormData update(AttributeFormData formData) throws ProcessingException;

	/**
	 * Fetches all meta data attributes which belongs to the file type with the
	 * assigned file type id [attribute id, name, data type uid, file type id]
	 * 
	 * @param filetypeId
	 *            Long
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getAttributes(Long filetypeId) throws ProcessingException;
	
	/**
	 * Return the attribute id for the assigned attribute name
	 * 
	 * @param attributeName
	 *            String
	 * @return Long
	 * @throws ProcessingException
	 */
	public Long getAttributeId(String attributeName) throws ProcessingException;
	
	/**
	 * Fetches all attributes for all file types [attribute id, name, data type]
	 * 
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getDetailedMetadataForAllFileTypes() throws ProcessingException;
}
