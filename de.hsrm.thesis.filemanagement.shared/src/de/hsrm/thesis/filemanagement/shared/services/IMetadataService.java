package de.hsrm.thesis.filemanagement.shared.services;

import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;
/**
 * Service Interface for metadata handling
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IMetadataService extends IService2 {

	/**
	 * Adds a meta data value to a file represented by the assigned file id.
	 * meta data value belongs to meta data attribute with the assigned
	 * attribute id
	 * 
	 * @param attributeId
	 *            Long
	 * @param fileId
	 *            Long
	 * @param metadata
	 *            String
	 * @throws ProcessingException
	 */
	public void addMetadata(Long attributeId, Long fileId, String metadata)
			throws ProcessingException;

	/**
	 * Fetches a matrix of user defined attributes for one or all file types
	 * [attribute id, name, value, data type]
	 * 
	 * @param fileId
	 *            Long
	 * @param filetypeId
	 *            Long
	 * @param onlyAttributesForFiletyp
	 *            boolean
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getMetadataAttributeMatrix(Long fileId, Long filetypeId,
			boolean onlyAttributesForFiletyp) throws ProcessingException;

	/**
	 * Returns the meta data value for one attribute and one file
	 * 
	 * @param attributeName
	 *            String
	 * @param fileId
	 *            Long
	 * @return Object
	 * @throws ProcessingException
	 */
	public Object getMetadataValue(String attributeName, Long fileId)
			throws ProcessingException;

	/**
	 * Returns a map of attribute name and value-pairs for one file
	 * 
	 * @param fileId
	 *            Long
	 * @param filetypeId
	 *            Long
	 * @return Map<String, Object>
	 * @throws ProcessingException
	 */
	public Map<String, Object> getMetdataMapForFile(Long fileId, Long filetypeId)
			throws ProcessingException;

	/**
	 * Modifies or creates meta data value for one file
	 * 
	 * @param attributeId
	 *            Long
	 * @param fileId
	 *            Long
	 * @param metadata
	 *            String
	 * @throws ProcessingException
	 */
	public void updateOrInsertMetadata(Long attributeId, Long fileId,
			String metadata) throws ProcessingException;

	/**
	 * Fetches meta data for all files with one of the assigned file ids. Only
	 * meta data belonging to the assigned attribute names will be fetched
	 * 
	 * @param fileIds
	 *            Long[]
	 * @param attributeNames
	 *            String...
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getMetadataForFiles(Long[] fileIds,
			String... attributeNames) throws ProcessingException;
}
