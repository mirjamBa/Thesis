package de.hsrm.thesis.filemanagement.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.ui.forms.FileFormatForm.MainBox.CancelButton;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileFormatForm.MainBox.FileFormatBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileFormatForm.MainBox.FileFormatBox.FileFormatField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileFormatForm.MainBox.FileFormatBox.FileTypeField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileFormatForm.MainBox.OkButton;
import de.hsrm.thesis.filemanagement.shared.services.IFileFormatService;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.formdata.FileFormatFormData;

@FormData(value = FileFormatFormData.class, sdkCommand = SdkCommand.CREATE)
public class FileFormatForm extends AbstractForm {

	private Long m_id;

	public FileFormatForm() throws ProcessingException {
		super();
	}

	/**
	 * @return the id
	 */
	@FormData
	public Long getId() {
		return m_id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@FormData
	public void setId(Long id) {
		m_id = id;
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
	}

	public void startModify() throws ProcessingException {
		startInternal(new ModifyHandler());
	}

	public void startNew() throws ProcessingException {
		startInternal(new NewHandler());
	}

	public FileFormatBox getFileFormatBox() {
		return getFieldByClass(FileFormatBox.class);
	}

	public FileFormatField getFileFormatField() {
		return getFieldByClass(FileFormatField.class);
	}

	public FileTypeField getFileTypeField() {
		return getFieldByClass(FileTypeField.class);
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
		public class FileFormatBox extends AbstractGroupBox {

			@Override
			protected String getConfiguredLabel() {
				return TEXTS.get("FileFormat");
			}

			@Order(20.0)
			public class FileFormatField extends AbstractStringField {

				@Override
				protected String getConfiguredLabel() {
				  return TEXTS.get("FileFormat");
				}

				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}
			}

			@Order(30.0)
			public class FileTypeField extends AbstractSmartField<Long> {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("FileType");
				}

				@Override
				protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
					return FileTypeCodeType.class;
				}

				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}
			}
		}

		@Order(30.0)
		public class OkButton extends AbstractOkButton {
		}

		@Order(40.0)
		public class CancelButton extends AbstractCancelButton {
		}
	}

	public class ModifyHandler extends AbstractFormHandler {

		@Override
		public void execStore() throws ProcessingException {
			IFileFormatService service = SERVICES
					.getService(IFileFormatService.class);
			FileFormatFormData formData = new FileFormatFormData();
			exportFormData(formData);
			formData = service.update(formData);
		}
	}

	public class NewHandler extends AbstractFormHandler {

		@Override
		public void execStore() throws ProcessingException {
			IFileFormatService service = SERVICES
					.getService(IFileFormatService.class);
			FileFormatFormData formData = new FileFormatFormData();
			exportFormData(formData);
			formData = service.create(formData);
		}
	}
}
