package de.hsrm.thesis.filemanagement.client.handler;

import java.io.File;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.ui.forms.FiletypeChooserForm;
import de.hsrm.thesis.filemanagement.shared.nonFormdataBeans.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.services.IFileFormatService;

public class MultipleFiletypesHandler extends AbstractHandler
		implements
			IHandler {

	@Override
	public void handle(File dropfile, ServerFileData fileData,
			Map<String, String> metaValues, String fileformat, Long filetypeNr, Long parentFolderId)
			throws ProcessingException {
		// check if fileformat is assigned to filetype
		// multiple
		if (SERVICES.getService(IFileFormatService.class)
				.isFormatMultipleAssigned(fileformat)) {
			// force choosing one of the assigned filetypes
			FiletypeChooserForm ft = new FiletypeChooserForm();
			ft.getFileNameField().setValue(
					fileData.getOldName() + "." + fileData.getFileExtension());
			ft.setFileformat(fileformat);
			ft.startNew();
			ft.waitFor();
			filetypeNr = ft.getFiletypeNr();
		}

		// extract filetype
		if (filetypeNr == null) {
			filetypeNr = SERVICES.getService(IFileFormatService.class)
					.getFiletypeForFileFormat(fileformat);
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