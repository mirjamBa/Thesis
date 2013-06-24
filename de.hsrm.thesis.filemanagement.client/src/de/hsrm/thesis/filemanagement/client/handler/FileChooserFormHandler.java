package de.hsrm.thesis.filemanagement.client.handler;

import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;

import de.hsrm.thesis.filemanagement.client.ui.forms.FileChooserForm;
import de.hsrm.thesis.filemanagement.shared.nonFormdataBeans.ServerFileData;

public class FileChooserFormHandler extends AbstractHandler implements IHandler {

	@Override
	public void handle(ServerFileData fileData, Map<String, String> metaValues,
			String fileformat, Long filetypeNr) throws ProcessingException {
		// choose file from filesystem
		FileChooserForm form = new FileChooserForm();
		form.startNew();
		form.waitFor();
		if (form.isFormStored()) {
			// extract data
			fileData = form.getFileData();
			metaValues = form.getMetaValues();
			fileformat = fileData.getFileformat();
			if (nextHandler != null) {
				nextHandler
						.handle(fileData, metaValues, fileformat, filetypeNr);
			}
		}
	}

	@Override
	public void setNext(IHandler handler) {
		this.nextHandler = handler;
	}

}
