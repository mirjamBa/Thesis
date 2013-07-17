package de.hsrm.mi.administration.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.administration.client.ui.forms.AttributeForm.MainBox.AttributeBox;
import de.hsrm.mi.administration.client.ui.forms.AttributeForm.MainBox.AttributeBox.DatatypeField;
import de.hsrm.mi.administration.client.ui.forms.AttributeForm.MainBox.AttributeBox.DescriptionField;
import de.hsrm.mi.administration.client.ui.forms.AttributeForm.MainBox.AttributeBox.FileTypeField;
import de.hsrm.mi.administration.client.ui.forms.AttributeForm.MainBox.AttributeBox.ShowInFileTableField;
import de.hsrm.mi.administration.client.ui.forms.AttributeForm.MainBox.CancelButton;
import de.hsrm.mi.administration.client.ui.forms.AttributeForm.MainBox.OkButton;
import de.hsrm.thesis.filemanagement.shared.services.IAttributeService;
import de.hsrm.thesis.filemanagement.shared.services.code.DatatypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.formdata.AttributeFormData;

@FormData(value = AttributeFormData.class, sdkCommand = SdkCommand.CREATE)
public class AttributeForm extends AbstractForm {

	private Long m_attributeId;

	public AttributeForm() throws ProcessingException {
		super();
	}

	public DescriptionField getDescriptionField() {
		return getFieldByClass(DescriptionField.class);
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

	public DatatypeField getDatatypeField() {
		return getFieldByClass(DatatypeField.class);
	}

	public FileTypeField getFileTypeField() {
		return getFieldByClass(FileTypeField.class);
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public AttributeBox getMetadataBox() {
		return getFieldByClass(AttributeBox.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	public ShowInFileTableField getShowInFileTableField() {
		return getFieldByClass(ShowInFileTableField.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Override
		protected int getConfiguredGridColumnCount() {
			return 1;
		}

		@Order(30.0)
		public class AttributeBox extends AbstractGroupBox {

			@Override
			protected String getConfiguredLabel() {
				return TEXTS.get("Metadata");
			}

			@Order(10.0)
			public class DescriptionField extends AbstractStringField {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Description");
				}
			}

			@Order(20.0)
			public class ShowInFileTableField extends AbstractCheckBox {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("ShowInFileTable");
				}
			}

			@Order(30.0)
			public class DatatypeField extends AbstractSmartField<Long> {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Datatype");
				}

				@Override
				protected Class<? extends ICodeType<?>> getConfiguredCodeType() {
					return DatatypeCodeType.class;
				}

			}

			@Order(40.0)
			public class FileTypeField extends AbstractSmartField<Long> {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("FileType");
				}

				@Override
				protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
					return FileTypeCodeType.class;
				}
			}
		}

		@Order(40.0)
		public class OkButton extends AbstractOkButton {
		}

		@Order(50.0)
		public class CancelButton extends AbstractCancelButton {
		}
	}

	public class ModifyHandler extends AbstractFormHandler {

		@Override
		public void execStore() throws ProcessingException {
			IAttributeService service = SERVICES
					.getService(IAttributeService.class);
			AttributeFormData formData = new AttributeFormData();
			exportFormData(formData);
			formData = service.update(formData);
		}
	}

	public class NewHandler extends AbstractFormHandler {

		@Override
		public void execStore() throws ProcessingException {
			IAttributeService service = SERVICES
					.getService(IAttributeService.class);
			AttributeFormData formData = new AttributeFormData();
			exportFormData(formData);
			formData = service.create(formData);
		}
	}

	@FormData
	public Long getAttributeId() {
		return m_attributeId;
	}

	@FormData
	public void setAttributeId(Long attributeId) {
		m_attributeId = attributeId;
	}
}
