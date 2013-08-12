package de.hsrm.perfunctio.core.server.handler;

import de.hsrm.perfunctio.core.shared.beans.ServerFileData;
import de.hsrm.perfunctio.core.shared.handler.AbstractHandler;
import de.hsrm.perfunctio.core.shared.services.formdata.FileFormData;

/**
 * Abstract Handler for the server file upload chain of responsibility
 * 
 * @author Mirjam Bayatloo
 * 
 */
public abstract class AbstractServerHandler extends AbstractHandler {
	
	/**
	 * Handles the file upload on server. Access to ServerFile by IFileService:
	 * getServerFile(fileId)
	 * 
	 * @param fileId
	 *            Long
	 * @param formData
	 *            FileFormData
	 * @param fileData
	 *            ServerFileData
	 * @param parentFolderId
	 *            Long
	 */
	public abstract void handle(Long fileId, FileFormData formData,
			ServerFileData fileData, Long parentFolderId);

}
