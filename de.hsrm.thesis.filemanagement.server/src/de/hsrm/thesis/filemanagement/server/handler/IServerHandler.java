package de.hsrm.thesis.filemanagement.server.handler;

import de.hsrm.thesis.filemanagement.shared.beans.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.services.formdata.FileFormData;

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
