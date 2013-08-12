package de.hsrm.perfunctio.core.shared.services;

import java.util.ArrayList;
import java.util.Map;

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
	 * frees complete folder with files and child folders
	 * 
	 * @param folderId
	 * @param roleIds
	 * @throws ProcessingException
	 */
	public void addFreeingToChildFolderAndFiles(Long folderId, Long[] roleIds,
			Map<Long, ArrayList<String>> permissionMapping)
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
	public void addFileFolderFreeing(Long folderId, Long[] roleIds,
			Map<Long, ArrayList<String>> permissionMapping)
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
			Map<Long, ArrayList<String>> permissions)
			throws ProcessingException;

	/**
	 * Deprives only file or folder of the assigned roles
	 * 
	 * @param id
	 * @param roles
	 * @throws ProcessingException
	 */
	void removeFreeingFromFolderOrFile(Long id,
			Map<Long, ArrayList<String>> permissions)
			throws ProcessingException;

	/**
	 * Modifies file or folder access permission for the assigned id. Access for
	 * all roles with one of the assigned role ids
	 * 
	 * @param fildId
	 *            Long
	 * @param roleIds
	 *            Long[]
	 * @throws ProcessingException
	 */
	public void freeFileOrFolder(Long fileId, Long[] roleIds,
			Map<Long, ArrayList<String>> permissionMapping)
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
	public void updateRoleFileAndFolderPermission(Long folderId,
			Long[] roleIds, Map<Long, ArrayList<String>> permissionMapping)
			throws ProcessingException;

	/**
	 * Fetches all role ids, the assigned file is freed for
	 * 
	 * @param fileId
	 *            Long
	 * @return Long[]
	 * @throws ProcessingException
	 */
	public Long[] getApprovedRolesForFileOrFolder(Long fileId)
			throws ProcessingException;

	/**
	 * Collects every permission for every role, for which the assigned file is
	 * freed
	 * 
	 * @param fileId
	 *            Long
	 * @return Map<Long, ArrayList<String>>
	 * @throws ProcessingException
	 */
	public Map<Long, ArrayList<String>> getRolePermissionsForFileOrFolder(
			Long fileId) throws ProcessingException;

}
