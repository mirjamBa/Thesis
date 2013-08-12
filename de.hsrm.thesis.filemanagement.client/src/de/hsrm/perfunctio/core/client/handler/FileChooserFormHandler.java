package de.hsrm.perfunctio.core.client.handler;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;

import de.hsrm.perfunctio.core.client.ui.forms.FileChooserForm;

/**
 * Concrete client handler, responsible for providing the file chooser form.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class FileChooserFormHandler extends AbstractClientHandler {
	private final int PRIORITY = 100;

	public FileChooserFormHandler() {
		this.priority = PRIORITY;
	}

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
				data.setFileFormat(IOUtility.getFileExtension(data
						.getServerFileData().getPath()));
				data.setMetaValues(form.getMetaValues());
				doNext(data);
			}
			// don't dispatch if form isn't stored and no dropfile is available
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
