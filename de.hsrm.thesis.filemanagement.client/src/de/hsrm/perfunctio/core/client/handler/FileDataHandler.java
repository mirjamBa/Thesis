package de.hsrm.perfunctio.core.client.handler;

import java.util.Date;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractValueField;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.client.ui.forms.FileForm;
import de.hsrm.perfunctio.core.shared.services.IFileService;
import de.hsrm.perfunctio.core.shared.services.IUserProcessService;
import de.hsrm.perfunctio.core.shared.services.formdata.FileFormData;

/**
 * Concrete client handler, responsible for preparing, providing and converting
 * the file form.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class FileDataHandler extends AbstractClientHandler {
	private final int PRIORITY = 500;
	private FileForm frm;
	private FileFormData frmData;

	public FileDataHandler() {
		this.priority = PRIORITY;
	}

	/**
	 * @return the frmData
	 */
	public FileFormData getFrmData() {
		return frmData;
	}

	/**
	 * @param frmData
	 *            the frmData to set
	 */
	public void setFrmData(FileFormData frmData) {
		this.frmData = frmData;
	}

	/**
	 * @return the frm
	 */
	public FileForm getFrm() {
		return frm;
	}

	/**
	 * @param frm
	 *            the frm to set
	 */
	public void setFrm(FileForm frm) {
		this.frm = frm;
	}

	@Override
	public void handle(FileUploadData data) throws ProcessingException {

		// prepare fileform
		frm = fillFileForm(data);

		if (data.getFile() == null) {
			// show form, user can modify data
			frm.startNew();
			frm.touch();
			frm.waitFor();

			if (frmData == null) {
				frmData = new FileFormData();
			}
			frm.exportFormData(frmData);
			data.setFileFormData(frmData);
			data.setFileId(frm.getFileNr());

		} else {
			// drag and drop action: quick processing required
			if (frmData == null) {
				frmData = new FileFormData();
			}
			frm.exportFormData(frmData);
			data.setFileFormData(frmData);

			frmData = SERVICES.getService(IFileService.class).create(frmData,
					data.getServerFileData(), frm.getParentFolderId());
			data.setFileId(frmData.getFileNr());
		}
		frm = null;
		frmData = null;

		if (nextHandler != null) {
			((AbstractClientHandler) nextHandler).handle(data);
		}
	}

	private FileForm fillFileForm(FileUploadData data)
			throws ProcessingException {
		if (frm == null) {
			frm = new FileForm(data.getServerFileData(), data.getFiletypeNr(),
					data.getParentFolderId());
		} else {
			frm.setFileData(data.getServerFileData());
			frm.setFiletypeNr(data.getFiletypeNr());
			frm.setParentFolderId(data.getParentFolderId());
		}

		// extracted meta values
		IFormField[] fields = frm.getDCMIBox().getFields();
		for (IFormField f : fields) {
			String elementName = f.getFieldId().replace("Field", "");
			AbstractValueField<?> vField = (AbstractValueField<?>) frm
					.getDCMIBox().getFieldByClass(f.getClass());
			if (data.getMetaValues().containsKey(elementName.toUpperCase())) {
				vField.parseValue(data.getMetaValues().get(
						elementName.toUpperCase()));
			} else if (data.getMetaValues().containsKey(
					("dc:" + elementName).toUpperCase())) {
				vField.parseValue(data.getMetaValues().get(
						("dc:" + elementName).toUpperCase()));
			}
			vField.touch();
		}

		// most important fields
		if (frm.getTitleField().isEmpty()) {
			frm.getTitleField().setValue(data.getServerFileData().getOldName());
		}
		if (frm.getFormatField().isEmpty()) {
			frm.getFormatField().setValue(
					data.getMetaValues().get("Content-Type".toUpperCase()));
		}
		if (frm.getDateMetadataField().isEmpty()) {
			frm.getDateMetadataField().setValue(
					data.getServerFileData().getLastModified());
		}

		frm.getTypistField().setValue(
				SERVICES.getService(IUserProcessService.class)
						.getCurrentUserId());
		frm.getFileExtensionField().setValue(data.getFileFormat());
		frm.getFileTypeField().setValue(data.getFiletypeNr());
		frm.getFileTypeField().setEnabled(false);
		frm.getCreationDateField().setValue(new Date());
		frm.getFilesizeField().setValue(data.getServerFileData().getFilesize());

		return frm;
	}

}
