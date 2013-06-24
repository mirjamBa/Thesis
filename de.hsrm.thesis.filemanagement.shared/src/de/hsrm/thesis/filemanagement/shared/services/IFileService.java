package de.hsrm.thesis.filemanagement.shared.services;

import java.io.File;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import de.hsrm.thesis.filemanagement.shared.formdata.FileFormData;
import de.hsrm.thesis.filemanagement.shared.formdata.FileSearchFormData;
import de.hsrm.thesis.filemanagement.shared.nonFormdataBeans.ServerFileData;

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
	public FileFormData create(FileFormData formData, ServerFileData fileData) throws ProcessingException;

	/**
	 * Modifies the assigned file data in the storage
	 * 
	 * @param formData
	 *            FileFormData
	 * @return FileFormData
	 * @throws ProcessingException
	 */
	public FileFormData update(FileFormData formData) throws ProcessingException;

	/**
	 * Fetches all files which conforms to the assigned search criteria [file
	 * id, file type id, file number, user id, file path]
	 * 
	 * @param searchFormData
	 *            FileSearchFormData
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getFiles(FileSearchFormData searchFormData) throws ProcessingException;

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
	 * Modifies file access permission for the assigned files. Access for all
	 * roles with one of the assigned role ids
	 * 
	 * @param fildId
	 *            Long
	 * @param roleIds
	 *            Long[]
	 * @throws ProcessingException
	 */
	public void updateRoleFilePermission(Long fildId, Long[] roleIds) throws ProcessingException;

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
}
