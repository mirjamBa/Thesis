package de.hsrm.perfunctio.core.shared.services;

import java.io.File;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

import de.hsrm.perfunctio.core.shared.beans.ServerFileData;

/**
 * Service interface for storing files
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFileStorageService extends IService {

	/**
	 * Saves the file to the configured storag-file path
	 * 
	 * @param fileData
	 *            ServerFileData
	 * @throws ProcessingException
	 */
	public void saveFile(ServerFileData fileData) throws ProcessingException;

	/**
	 * Create the ServerFileData based on the assigned file
	 * 
	 * @param file
	 *            File
	 * @return ServerFileData
	 * @throws ProcessingException
	 */
	public ServerFileData getServerFileData(File file)
			throws ProcessingException;

	/**
	 * Deletes the stored file
	 * 
	 * @param path
	 *            String
	 * @return boolean
	 */
	public boolean deleteServerFile(String path);

}
