package de.hsrm.perfunctio.core.client.handler;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.client.ui.forms.FiletypeChooserForm;
import de.hsrm.perfunctio.core.shared.services.IFileFormatService;

public class MultipleFiletypesHandler extends AbstractClientHandler
		implements
			IClientHandler {

	@Override
	public void handle(FileUploadData data) throws ProcessingException {
		// check if fileformat is assigned to filetype
		// multiple
		if (SERVICES.getService(IFileFormatService.class)
				.isFormatMultipleAssigned(data.getFileFormat())) {
			// force choosing one of the assigned filetypes
			FiletypeChooserForm ft = new FiletypeChooserForm();
			ft.getFileNameField().setValue(
					data.getServerFileData().getOldName() + "." + data.getFileFormat());
			ft.setFileformat(data.getFileFormat());
			ft.startNew();
			ft.waitFor();
			data.setFiletypeNr(ft.getFiletypeNr());
			if (ft.isFormStored()) {
				doNext(data);
			}
		} else {

			// extract filetype
			if (data.getFiletypeNr() == null) {
				data.setFiletypeNr(SERVICES.getService(IFileFormatService.class)
						.getFiletypeForFileFormat(data.getFileFormat()));
			}

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
