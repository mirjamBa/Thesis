package de.hsrm.thesis.filemanagement.client.handler;

import java.io.File;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.ui.forms.FileFormatForm;
import de.hsrm.thesis.filemanagement.shared.nonFormdataBeans.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.services.IFileFormatService;

public class UnknownFileformatHandler extends AbstractHandler
		implements
			IHandler {

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
		}
		if (nextHandler != null) {
			nextHandler.handle(dropfile, fileData, metaValues, fileformat,
					filetypeNr, parentFolderId);
		}
	}

	@Override
	public void setNext(IHandler handler) {
		this.nextHandler = handler;

	}

}