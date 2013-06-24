package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import de.hsrm.thesis.filemanagement.shared.services.formdata.FileFormatFormData;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFileFormatService extends IService2 {

	/**
	 * Saves new file formats into storage
	 * 
	 * @param formData
	 *            FileFormatFormData
	 * @return the assigned FileFormatFormData
	 * @throws ProcessingException
	 */
	public FileFormatFormData create(FileFormatFormData formData) throws ProcessingException;

	/**
	 * Deletes all file formats from storage having one of the assigned ids
	 * 
	 * @param ids
	 *            Long[]
	 * @throws ProcessingException
	 */
	public void delete(Long[] ids) throws ProcessingException;

	/**
	 * Modifies the assigned file format in the storage
	 * 
	 * @param formData
	 *            FileFormatFormData
	 * @return the assigned FileFormatFormData
	 * @throws ProcessingException
	 */
	public FileFormatFormData update(FileFormatFormData formData) throws ProcessingException;

	/**
	 * Fetches all formats which belongs to the assigned file type id from
	 * storage [file format id, format, file type]
	 * 
	 * @param filetypeNr
	 *            Long
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getFileFormats(Long filetypeNr) throws ProcessingException;

	/**
	 * Decides whether there are more than one file type the assigned file
	 * format belongs to
	 * 
	 * @param fileformat
	 *            String
	 * @return boolean [true for multiple file format - file type connections]
	 * @throws ProcessingException
	 */
	public boolean isFormatMultipleAssigned(String fileformat) throws ProcessingException;

	/**
	 * Fetches the file type id for the assigned file format from storage
	 * 
	 * @param fileformat
	 *            String
	 * @return Long [file type id]
	 * @throws ProcessingException
	 */
	public Long getFiletypeForFileFormat(String fileformat) throws ProcessingException;

	/**
	 * Returns true if the assigned file format has already been stored
	 * 
	 * @param fileformat
	 *            String
	 * @return boolean
	 * @throws ProcessingException
	 */
	public boolean isFileformatRegistered(String fileformat) throws ProcessingException;

	/**
	 * Fetches an Array of ids of all file types, the assigned file format
	 * belongs to
	 * 
	 * @param fileformat
	 *            String
	 * @return Long[]
	 * @throws ProcessingException
	 */
	public Long[] getFiletypesForFileFormat(String fileformat) throws ProcessingException;
}
