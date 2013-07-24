package de.hsrm.perfunctio.core.client.handler;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.client.ui.forms.FileFormatForm;
import de.hsrm.perfunctio.core.shared.services.IFileFormatService;

public class UnknownFileformatHandler extends AbstractClientHandler
		implements
			IClientHandler {

	@Override
	public void handle(FileUploadData data)
			throws ProcessingException {
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
			if(fileformatForm.isFormStored()){
				doNext(data);
			}
			//don't dispatch if form isn't stored and no format is chosen
		}else{
		doNext(data);
		}
	}
	
	private void doNext(FileUploadData data) throws ProcessingException {
		if (nextHandler != null) {
			nextHandler.handle(data);
		}
	}

	@Override
	public void setNext(IClientHandler handler) {
		this.nextHandler = handler;

	}

}
