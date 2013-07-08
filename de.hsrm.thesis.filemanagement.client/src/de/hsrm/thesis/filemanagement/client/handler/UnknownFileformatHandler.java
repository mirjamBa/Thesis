package de.hsrm.thesis.filemanagement.client.handler;

import java.io.File;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.ui.forms.FileFormatForm;
import de.hsrm.thesis.filemanagement.shared.beans.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.services.IFileFormatService;

public class UnknownFileformatHandler extends AbstracClienttHandler
		implements
			IClientHandler {

	@Override
	public void handle(File dropfile, ServerFileData fileData,
			Map<String, String> metaValues, String fileformat, Long filetypeNr, Long parentFolderId)
			throws ProcessingException {
		// check if format is registered and linked with
		// filetype
		if (!SERVICES.getService(IFileFormatService.class)
				.isFileformatRegistered(fileformat)) {
			// force to register fileformat
			FileFormatForm fileformatForm = new FileFormatForm();
			fileformatForm.getFileFormatField().setValue(fileformat);
			fileformatForm.getFileFormatField().setEnabled(false);
			fileformatForm.startNew();
			fileformatForm.waitFor();
			if(fileformatForm.isFormStored()){
				doNext(dropfile, fileData, metaValues, fileformat, filetypeNr,
						parentFolderId);
			}
			//don't dispatch if form isn't stored and no format is chosen
		}else{
		doNext(dropfile, fileData, metaValues, fileformat, filetypeNr,
				parentFolderId);
		}
	}
	
	private void doNext(File dropfile, ServerFileData fileData,
			Map<String, String> metaValues, String fileformat, Long filetypeNr,
			Long parentFolderId) throws ProcessingException {
		if (nextHandler != null) {
			nextHandler.handle(dropfile, fileData, metaValues, fileformat,
					filetypeNr, parentFolderId);
		}
	}

	@Override
	public void setNext(IClientHandler handler) {
		this.nextHandler = handler;

	}

}
