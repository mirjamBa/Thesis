package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.service.IService;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;

import de.hsrm.thesis.filemanagement.shared.services.formdata.MetadataFormData;

@InputValidation(IValidationStrategy.PROCESS.class)
	public interface IUserDefinedAttributesService extends IService{
	
	/**
	 * Saves new meta data attribute into storage
	 * 
	 * @param formData
	 *            MetadataFormData
	 * @return MetadataFormData
	 * @throws ProcessingException
	 */
	public MetadataFormData create(MetadataFormData formData) throws ProcessingException;

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
	public MetadataFormData update(MetadataFormData formData) throws ProcessingException;

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
