package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

import de.hsrm.thesis.filemanagement.shared.services.formdata.FoldersFormData;

/**
 * Service Handling for folder handling
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFolderService extends IService {

	/**
	 * Saves folder data to storage
	 * 
	 * @param formData
	 *            FoldersFormData
	 * @param parentFolderId
	 *            Long
	 * @return FoldersFormData
	 * @throws ProcessingException
	 */
	public FoldersFormData createFolder(FoldersFormData formData,
			Long parentFolderId) throws ProcessingException;

	/**
	 * Removes only the folder from storage, no child folders or files will be
	 * deleted
	 * 
	 * @param folderId
	 *            Long
	 * @throws ProcessingException
	 */
	public void deleteFolder(Long folderId) throws ProcessingException;

	/**
	 * Removes folder data and all files and folders with the assigned id as
	 * parent folder from storage
	 * 
	 * @param folderId
	 *            Long
	 * @throws ProcessingException
	 */
	public void deleteFolderAndChildFolders(Long folderId)
			throws ProcessingException;

	/**
	 * Changes folder data in storage
	 * 
	 * @param formData
	 *            FoldersFormData
	 * @return FoldersFormData
	 * @throws ProcessingException
	 */
	public FoldersFormData updateFolder(FoldersFormData formData)
			throws ProcessingException;

	/**
	 * Fetches the parent folder id from storage for the assigned file or folder
	 * id
	 * 
	 * @param fileOrFolderId
	 *            Long
	 * @return Long
	 * @throws ProcessingException
	 */
	public Long getParentFolderId(Long fileOrFolderId)
			throws ProcessingException;
	
	/**
	 * Returns a hierarchical info about the folder structure
	 * @param fileOrFolderId
	 * @return
	 * @throws ProcessingException
	 */
	public String getFolderHierarchy(Long fileOrFolderId) throws ProcessingException;

}
