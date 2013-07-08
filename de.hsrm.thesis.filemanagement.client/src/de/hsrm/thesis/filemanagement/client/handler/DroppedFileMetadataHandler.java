package de.hsrm.thesis.filemanagement.client.handler;

import java.io.File;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.shared.beans.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.ITikaService;

public class DroppedFileMetadataHandler extends AbstracClienttHandler
		implements
			IClientHandler {

	@Override
	public void handle(File dropfile, ServerFileData fileData, Map<String, String> metaValues,
			String fileformat, Long filetypeNr, Long parentFolderId) throws ProcessingException {
		if(dropfile != null){
			// extract data
			fileData = SERVICES.getService(IFileService.class).saveFile(dropfile);
			metaValues = SERVICES.getService(ITikaService.class).extractDataFromFile(dropfile);
			fileformat = fileData.getFileformat();
		}

		if (nextHandler != null) {
			nextHandler.handle(dropfile, fileData, metaValues, fileformat, filetypeNr, parentFolderId);
		}

	}

	@Override
	public void setNext(IClientHandler handler) {
		this.nextHandler = handler;
	}

}
