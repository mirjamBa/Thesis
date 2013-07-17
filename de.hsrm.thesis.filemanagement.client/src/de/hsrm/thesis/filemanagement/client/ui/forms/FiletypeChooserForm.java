package de.hsrm.thesis.filemanagement.client.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.ui.forms.FiletypeChooserForm.MainBox.CancelButton;
import de.hsrm.thesis.filemanagement.client.ui.forms.FiletypeChooserForm.MainBox.FiletypeChooserFormBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FiletypeChooserForm.MainBox.FiletypeChooserFormBox.FileNameField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FiletypeChooserForm.MainBox.FiletypeChooserFormBox.FileTypeField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FiletypeChooserForm.MainBox.FiletypeChooserFormBox.FiletypeChooserFormField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FiletypeChooserForm.MainBox.OkButton;
import de.hsrm.thesis.filemanagement.shared.services.IFileFormatService;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.formdata.FiletypeChooserFormData;

@FormData(value = FiletypeChooserFormData.class, sdkCommand = SdkCommand.CREATE)
public class FiletypeChooserForm extends AbstractForm {

	private String m_fileformat;
	private long m_filetypeNr;

	public FiletypeChooserForm() throws ProcessingException {
		super();
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("FiletypeChooserForm");
	}

	public void startNew() throws ProcessingException {
		startInternal(new NewHandler());
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
	}

	public FileNameField getFileNameField() {
		return getFieldByClass(FileNameField.class);
	}

	public FileTypeField getFileTypeField() {
		return getFieldByClass(FileTypeField.class);
	}

	public FiletypeChooserFormBox getFiletypeChooserFormBox() {
		return getFieldByClass(FiletypeChooserFormBox.class);
	}

	public FiletypeChooserFormField getFiletypeChooserFormField() {
		return getFieldByClass(FiletypeChooserFormField.class);
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Override
		protected int getConfiguredGridColumnCount() {
			return 1;
		}

		@Order(10.0)
		public class FiletypeChooserFormBox extends AbstractGroupBox {

			@Order(10.0)
			public class FiletypeChooserFormField extends AbstractHtmlField {

				@Override
				protected boolean getConfiguredLabelVisible() {
					return false;
				}

				@Override
				protected int getConfiguredGridH() {
					return 2;
				}

				@Override
				protected void execInitField() throws ProcessingException {
					setValue(TEXTS.get("FiletypeChooserFormLabel"));
				}

			}

			@Order(20.0)
			public class FileTypeField extends AbstractSmartField<Long> {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("FileType");
				}

				@Override
				protected Class<? extends ICodeType<?>> getConfiguredCodeType() {
					return FileTypeCodeType.class;
				}

				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}

				@Override
				protected void execFilterLookupResult(LookupCall call,
						List<LookupRow> result) throws ProcessingException {
					// get all CodeIds the format is connected with
					Long[] ids = SERVICES.getService(IFileFormatService.class)
							.getFiletypesForFileFormat(getFileformat());
					ArrayList<Long> codeIds = new ArrayList<Long>();
					for (Long id : ids) {
						codeIds.add(id);
					}
					// remove other codes
					for (int i = 0; i < result.size();) {
						if (!codeIds.contains(result.get(i).getKey())) {
							result.remove(i);
						} else {
							i++;
						}
					}
				}
			}

			@Order(30.0)
			public class FileNameField extends AbstractStringField {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("FileName");
				}

				@Override
				protected boolean getConfiguredEnabled() {
					return false;
				}
			}
		}

		@Order(20.0)
		public class OkButton extends AbstractOkButton {
		}

		@Order(30.0)
		public class CancelButton extends AbstractCancelButton {
		}
	}

	public class NewHandler extends AbstractFormHandler {

		@Override
		public void execStore() throws ProcessingException {
			FiletypeChooserFormData formData = new FiletypeChooserFormData();
			exportFormData(formData);
			setFiletypeNr(formData.getFileType().getValue());
		}
	}

	@FormData
	public String getFileformat() {
		return m_fileformat;
	}

	@FormData
	public void setFileformat(String fileformat) {
		m_fileformat = fileformat;
	}

	@FormData
	public long getFiletypeNr() {
		return m_filetypeNr;
	}

	@FormData
	public void setFiletypeNr(long filetypeNr) {
		m_filetypeNr = filetypeNr;
	}
}
