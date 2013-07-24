package de.hsrm.perfunctio.core.server.handler;

import de.hsrm.perfunctio.core.shared.beans.ServerFileData;
import de.hsrm.perfunctio.core.shared.services.formdata.FileFormData;

public interface IServerHandler {
	
	/**
	 * Handles the file upload on server. Access to ServerFile by IFileService: getServerFile(fileId)
	 * 
	 * @param fileId Long
	 * @param formData FileFormData
	 * @param fileData ServerFileData
	 * @param parentFolderId Long
	 */
	public void handle(Long fileId, FileFormData formData, ServerFileData fileData,
			Long parentFolderId);
	
	public void setNext(IServerHandler handler);

}
