package de.hsrm.perfunctio.core.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import de.hsrm.perfunctio.core.shared.services.formdata.TagFormData;

/**
 * Service Interface for tag handling
 * 
 * @author mba
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface ITagService extends IService2 {

	public static final String TAG_SEPERATOR = ";";

	/**
	 * Saves new tag into storage
	 * 
	 * @param formData
	 *            TagFormData
	 * @return TagFormData
	 * @throws ProcessingException
	 */
	public TagFormData create(TagFormData formData) throws ProcessingException;

	/**
	 * Saves multiple tags into storage
	 * 
	 * @param tagnames
	 *            String...
	 * @throws ProcessingException
	 */
	public void create(String... tagnames) throws ProcessingException;

	/**
	 * Fetches all tags from storage [tag id, name]
	 * 
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getTags() throws ProcessingException;

	/**
	 * Modifies tag in the storage
	 * 
	 * @param formData
	 *            TagFormData
	 * @throws ProcessingException
	 */
	public void updateTag(TagFormData formData) throws ProcessingException;

	/**
	 * Removes all tags with one of the assigned ids
	 * 
	 * @param ids
	 *            Long[]
	 * @throws ProcessingException
	 */
	public void deleteTag(Long[] ids) throws ProcessingException;

	/**
	 * Fetches all tag ids the assigned file is dedicated with
	 * 
	 * @param fileId
	 *            Long
	 * @return Long[]
	 * @throws ProcessingException
	 */
	public Long[] getTagsForFile(Long fileId) throws ProcessingException;

	/**
	 * Extract all tag names from one string
	 * 
	 * @param newTags
	 *            String
	 * @return String[]
	 */
	public String[] filterTagnames(String newTags);

	/**
	 * Fetches the tag id for the assigned tag name
	 * 
	 * @param name
	 *            String
	 * @return Long
	 * @throws ProcessingException
	 */
	public Long getTagId(String name) throws ProcessingException;

	/**
	 * Returns true if there are defined tags in the storage
	 * 
	 * @return boolean
	 * @throws ProcessingException
	 */
	public boolean tagsExisting() throws ProcessingException;

	/**
	 * Deletes all tag assignments for the file
	 * 
	 * @param fileId
	 *            Long
	 * @throws ProcessingException
	 */
	public void deleteTagsForFile(Long fileId) throws ProcessingException;

}
