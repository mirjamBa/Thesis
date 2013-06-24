package de.hsrm.thesis.filemanagement.client.handler;

import java.util.Date;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractValueField;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm;
import de.hsrm.thesis.filemanagement.shared.nonFormdataBeans.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.services.IUserProcessService;

public class FileDataHandler extends AbstractHandler implements IHandler {

	@Override
	public void handle(ServerFileData fileData, Map<String, String> metaValues,
			String fileformat, Long filetypeNr) throws ProcessingException {
		// open and prepare fileform
		FileForm frm = new FileForm(fileData, filetypeNr);

		// extracted meta values
		IFormField[] fields = frm.getDCMIBox().getFields();
		for (IFormField f : fields) {
			String elementName = f.getFieldId().replace("Field", "");
			AbstractValueField<?> vField = (AbstractValueField<?>) frm
					.getDCMIBox().getFieldByClass(f.getClass());
			if (metaValues.containsKey(elementName.toUpperCase())) {
				vField.parseValue(metaValues.get(elementName.toUpperCase()));
			} else if (metaValues.containsKey(("dc:" + elementName)
					.toUpperCase())) {
				vField.parseValue(metaValues.get(("dc:" + elementName)
						.toUpperCase()));
			}
			vField.touch();
		}

		// most important fields
		if (frm.getTitleField().isEmpty()) {
			frm.getTitleField().setValue(fileData.getOldName());
		}
		if (frm.getFormatField().isEmpty()) {
			frm.getFormatField().setValue(
					metaValues.get("Content-Type".toUpperCase()));
		}
		if (frm.getDateMetadataField().isEmpty()) {
			frm.getDateMetadataField().setValue(fileData.getLastModified());
		}

		frm.getTypistField().setValue(
				SERVICES.getService(IUserProcessService.class)
						.getCurrentUserId());
		frm.getFileExtensionField().setValue(fileData.getFileExtension());
		frm.getFileTypeField().setValue(filetypeNr);
		frm.getFileTypeField().setEnabled(false);
		frm.getCreationDateField().setValue(new Date());
		frm.getFilesizeField().setValue(fileData.getFilesize());

		frm.startNew();
		frm.touch();
		frm.waitFor();
		if (frm.isFormStored()) {
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
