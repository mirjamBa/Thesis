package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

/**
 * Service Interface for approval handling
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFileAndFolderFreeingService extends IService {

	/**
	 * frees only the folder without data
	 * 
	 * @param folderId
	 * @param roleIds
	 * @throws ProcessingException
	 */
	public void freeFolder(Long folderId, Long[] roleIds)
			throws ProcessingException;

	/**
	 * frees complete folder with files and child folders
	 * 
	 * @param folderId
	 * @param roleIds
	 * @throws ProcessingException
	 */
	public void addFreeingToChildFolderAndFiles(Long folderId, Long[] roleIds)
			throws ProcessingException;

	/**
	 * Frees only the assigned file or folder to the roles
	 * 
	 * @param folderId
	 *            Long
	 * @param roleIds
	 *            Long[]
	 * @throws ProcessingException
	 */
	public void addFileFolderFreeing(Long folderId, Long[] roleIds)
			throws ProcessingException;

	/**
	 * Deprives folder (and child files and folders) of the assigned roles
	 * 
	 * @param parentFolderId
	 *            Long
	 * @param roleIds
	 *            Long[]
	 * @throws ProcessingException
	 */
	public void removeFreeingFromChildFolderAndFiles(Long parentFolderId,
			Long[] roleIds) throws ProcessingException;

	/**
	 * Deprives only file or folder of the assigned roles
	 * 
	 * @param id
	 * @param roles
	 * @throws ProcessingException
	 */
	void removeFreeingFromFolderOrFile(Long id, Long[] roles)
			throws ProcessingException;
	
	/**
	 * Modifies file or folder access permission for the assigned id. Access for all
	 * roles with one of the assigned role ids
	 * 
	 * @param fildId
	 *            Long
	 * @param roleIds
	 *            Long[]
	 * @throws ProcessingException
	 */
	public void freeFile(Long fileId, Long[] roleIds)
			throws ProcessingException;

	/**
	 * Changes the file or folder freeing for the assigned Id
	 * 
	 * @param fileId
	 *            Long
	 * @param roleIds
	 *            Long[]
	 * @throws ProcessingException
	 */
	public void updateRoleFileAndFolderPermission(Long fileId, Long[] roleIds)
			throws ProcessingException;

}
