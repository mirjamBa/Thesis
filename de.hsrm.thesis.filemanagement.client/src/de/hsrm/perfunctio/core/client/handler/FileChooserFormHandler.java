package de.hsrm.perfunctio.core.client.handler;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;

import de.hsrm.perfunctio.core.client.ui.forms.FileChooserForm;

public class FileChooserFormHandler extends AbstractClientHandler
		implements
			IClientHandler {

	@Override
	public void handle(FileUploadData data) throws ProcessingException {
		// not necessary for drag and drop action
		if (data.getFile() == null) {

			// choose file from filesystem
			FileChooserForm form = new FileChooserForm();
			form.startNew();
			form.waitFor();
			if (form.isFormStored()) {
				data.setServerFileData(form.getFileData());
				data.setFileFormat(IOUtility.getFileExtension(data.getServerFileData().getPath()));
				data.setMetaValues(form.getMetaValues());
				doNext(data);
			}
			//don't dispatch if form isn't stored and no dropfile is available
		} else {
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