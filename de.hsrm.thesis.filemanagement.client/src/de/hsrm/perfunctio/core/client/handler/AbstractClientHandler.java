package de.hsrm.perfunctio.core.client.handler;

import org.eclipse.scout.commons.exception.ProcessingException;

import de.hsrm.perfunctio.core.shared.handler.AbstractHandler;

/**
 * Abstract handler for the client file upload chain
 * 
 * @author Mirjam Bayatloo
 * 
 */
public abstract class AbstractClientHandler extends AbstractHandler {

	/**
	 * Handles the file upload process on client
	 * 
	 * @param data
	 * @throws ProcessingException
	 */
	public abstract void handle(FileUploadData data) throws ProcessingException;

}
