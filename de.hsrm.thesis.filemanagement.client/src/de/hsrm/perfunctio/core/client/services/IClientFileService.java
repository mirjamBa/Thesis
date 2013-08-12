package de.hsrm.perfunctio.core.client.services;

import java.io.File;

import org.eclipse.scout.service.IService;
import org.eclipse.scout.commons.exception.ProcessingException;

/**
 * Service interface for file handling on client side
 * 
 * @author Mirjam Bayatloo
 * 
 */
public interface IClientFileService extends IService {

	/**
	 * Displays the assigned file on Desktop
	 * 
	 * @param fileNr
	 *            Long
	 * @throws ProcessingException
	 */
	public void openFile(Long fileNr) throws ProcessingException;

	/**
	 * Downloads the assigned file to the tempfile path.
	 * 
	 * @param f
	 *            File
	 * @param tempfile
	 *            File
	 * @throws ProcessingException
	 */
	public void downloadFile(File f, File tempfile) throws ProcessingException;
}
