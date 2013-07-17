package de.hsrm.thesis.filemanagement.client.handler;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.ITikaService;

public class DroppedFileMetadataHandler extends AbstractClientHandler
		implements
			IClientHandler {

	@Override
	public void handle(FileUploadData data) throws ProcessingException {
		if(data.getFile() != null){
			// extract data
			data.setServerFileData(SERVICES.getService(IFileService.class).saveFile(data.getFile()));
			data.setMetaValues(SERVICES.getService(ITikaService.class).extractDataFromFile(data.getFile()));
		}

		if (nextHandler != null) {
			nextHandler.handle(data);
		}

	}

	@Override
	public void setNext(IClientHandler handler) {
		this.nextHandler = handler;
	}

}
