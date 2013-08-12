package de.hsrm.perfunctio.core.server.handler;

import org.eclipse.scout.commons.exception.ProcessingException;

import de.hsrm.perfunctio.core.shared.beans.ServerFileData;
import de.hsrm.perfunctio.core.shared.handler.AbstractHandlerManager;
import de.hsrm.perfunctio.core.shared.services.formdata.FileFormData;

/**
 * Handler container for managing all server handler and activating the handler
 * chain
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ServerHandlerManager extends AbstractHandlerManager {

	public ServerHandlerManager() {
		super();
	}

	public void handle(Long fileId, FileFormData formData,
			ServerFileData fileData, Long parentFolderId)
			throws ProcessingException {
		((AbstractServerHandler) handler.get(0)).handle(fileId, formData,
				fileData, parentFolderId);
	}
}
