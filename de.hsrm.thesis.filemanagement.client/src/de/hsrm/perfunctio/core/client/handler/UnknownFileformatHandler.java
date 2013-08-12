package de.hsrm.perfunctio.core.client.handler;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.client.ui.forms.FileFormatForm;
import de.hsrm.perfunctio.core.shared.services.IFileFormatService;

/**
 * Concrete client handler, responsible for providing a file format form if the
 * file ending of the upload-file is unknown.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class UnknownFileformatHandler extends AbstractClientHandler {
	private final int PRIORITY = 300;

	public UnknownFileformatHandler() {
		this.priority = PRIORITY;
	}

	@Override
	public void handle(FileUploadData data) throws ProcessingException {
		// check if format is registered and linked with
		// filetype
		if (!SERVICES.getService(IFileFormatService.class)
				.isFileformatRegistered(data.getFileFormat())) {
			// force to register fileformat
			FileFormatForm fileformatForm = new FileFormatForm();
			fileformatForm.getFileFormatField().setValue(data.getFileFormat());
			fileformatForm.getFileFormatField().setEnabled(false);
			fileformatForm.startNew();
			fileformatForm.waitFor();
			if (fileformatForm.isFormStored()) {
				doNext(data);
			}
			// don't dispatch if form isn't stored and no format is chosen
		} else {
			doNext(data);
		}
	}

	private void doNext(FileUploadData data) throws ProcessingException {
		if (nextHandler != null) {
			((AbstractClientHandler) nextHandler).handle(data);
		}
	}

}
