package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

import de.hsrm.thesis.filemanagement.shared.forms.FoldersFormData;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFolderService extends IService {

	public Object[][] getFolders(Long folderId) throws ProcessingException;

	public FoldersFormData createFolder(FoldersFormData formData,
			Long parentFolderId) throws ProcessingException;

	public void deleteFolderAndChildFolders(Long folderId) throws ProcessingException;

	public FoldersFormData updateFolder(FoldersFormData formData)
			throws ProcessingException;

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

	public Long getParentFolder(Long fileOrFolderId) throws ProcessingException;

	public void addFileFolderFreeing(Long folderId, Long[] roleIds)
			throws ProcessingException;

	void removeFreeingFromChildFolderAndFiles(Long parentFolderId,
			Long[] roleIds) throws ProcessingException;

	public void slideFileOrFolder(Long slidedFFId, Long oldFolderId, Long newFolderId) throws ProcessingException;

	void deleteFolder(Long folderId) throws ProcessingException;
}
