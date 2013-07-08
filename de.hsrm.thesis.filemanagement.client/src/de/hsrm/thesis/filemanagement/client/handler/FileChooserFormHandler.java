package de.hsrm.thesis.filemanagement.client.handler;

import java.io.File;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;

import de.hsrm.thesis.filemanagement.client.ui.forms.FileChooserForm;
import de.hsrm.thesis.filemanagement.shared.beans.ServerFileData;

public class FileChooserFormHandler extends AbstracClienttHandler
		implements
			IClientHandler {

	@Override
	public void handle(File dropfile, ServerFileData fileData,
			Map<String, String> metaValues, String fileformat, Long filetypeNr,
			Long parentFolderId) throws ProcessingException {
		// not necessary for drag and drop action
		if (dropfile == null) {

			// choose file from filesystem
			FileChooserForm form = new FileChooserForm();
			form.startNew();
			form.waitFor();
			if (form.isFormStored()) {
				// extract data
				fileData = form.getFileData();
				metaValues = form.getMetaValues();
				fileformat = fileData.getFileformat();
				doNext(dropfile, fileData, metaValues, fileformat, filetypeNr,
						parentFolderId);
			}
			//don't dispatch if form isn't stored and no dropfile is available
		} else {
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
