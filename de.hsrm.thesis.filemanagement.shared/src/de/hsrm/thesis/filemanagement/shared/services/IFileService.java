package de.hsrm.thesis.filemanagement.shared.services;

import java.io.File;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import de.hsrm.thesis.filemanagement.shared.beans.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.services.formdata.FileFormData;
import de.hsrm.thesis.filemanagement.shared.services.formdata.FileSearchFormData;

/**
 * Service Interface for file handling
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFileService extends IService2 {

	/**
	 * Saves file data into storage
	 * 
	 * @param formData
	 *            FileFormData
	 * @param fileData
	 *            ServerFileData
	 * @return FileFormData
	 * @throws ProcessingException
	 */
	public FileFormData create(FileFormData formData, ServerFileData fileData,
			Long parentFolderId) throws ProcessingException;

	/**
	 * Modifies the assigned file data in the storage
	 * 
	 * @param formData
	 *            FileFormData
	 * @return FileFormData
	 * @throws ProcessingException
	 */
	public FileFormData update(FileFormData formData)
			throws ProcessingException;

	/**
	 * Fetches all files which conforms to the assigned search criteria and have
	 * the assigned parent folderId [file id, file type id, file number, user
	 * id, file path]
	 * 
	 * @param searchFormData
	 *            FileSearchFormData
	 * @param folderId
	 *            Long
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getFolderFiles(FileSearchFormData searchFormData,
			Long folderId) throws ProcessingException;

	/**
	 * Fetches all files which conforms to the assigned search criteria from
	 * every folder [file id, file type id, file number, user id, file path]
	 * 
	 * @param searchFormData
	 * @return
	 * @throws ProcessingException
	 */
	public Object[][] getFiles(FileSearchFormData searchFormData,
			int maxRowCount, int page) throws ProcessingException;

	/**
	 * Extracts content from file, create new file with this content and saves
	 * this copy to configured storage
	 * 
	 * @param file
	 *            File
	 * @return ServerFileData
	 * @throws ProcessingException
	 */
	public ServerFileData saveFile(File file) throws ProcessingException;

	/**
	 * Delete the file belonging to the assigned file id an all its meta data
	 * and permissions from storage
	 * 
	 * @param fileId
	 *            Long
	 * @return boolean
	 * @throws ProcessingException
	 */
	public boolean deleteFile(Long fileId) throws ProcessingException;

	/**
	 * Returns the file without meta data from storage
	 * 
	 * @param fileId
	 *            Long
	 * @return File
	 * @throws ProcessingException
	 */
	public File getServerFile(Long fileId) throws ProcessingException;

	/**
	 * Returns the number of files in storage for the assigned file type. 
	 * 
	 * @param filetype Long
	 * @return int
	 * @throws ProcessingException
	 */
	public int getNumberOfFiles(Long filetype) throws ProcessingException;

}
