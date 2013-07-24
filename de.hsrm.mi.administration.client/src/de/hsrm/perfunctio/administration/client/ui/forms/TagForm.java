package de.hsrm.perfunctio.administration.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.administration.client.ui.forms.TagForm.MainBox.CancelButton;
import de.hsrm.perfunctio.administration.client.ui.forms.TagForm.MainBox.OkButton;
import de.hsrm.perfunctio.administration.client.ui.forms.TagForm.MainBox.TagBox;
import de.hsrm.perfunctio.administration.client.ui.forms.TagForm.MainBox.TagBox.TagIdField;
import de.hsrm.perfunctio.administration.client.ui.forms.TagForm.MainBox.TagBox.TagNameField;
import de.hsrm.perfunctio.core.shared.services.ITagService;
import de.hsrm.perfunctio.core.shared.services.formdata.TagFormData;

@FormData(value = TagFormData.class, sdkCommand = SdkCommand.CREATE)
public class TagForm extends AbstractForm {

	public TagForm() throws ProcessingException {
		super();
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

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	public TagBox getTagBox() {
		return getFieldByClass(TagBox.class);
	}

	public TagIdField getTagIdField() {
		return getFieldByClass(TagIdField.class);
	}

	public TagNameField getTagNameField() {
		return getFieldByClass(TagNameField.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Override
		protected int getConfiguredGridColumnCount() {
			return 1;
		}

		@Order(20.0)
		public class TagBox extends AbstractGroupBox {

			@Override
			protected String getConfiguredLabel() {
				return TEXTS.get("Tag");
			}

			@Order(10.0)
			public class TagNameField extends AbstractStringField {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("TagName");
				}

				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}
			}

			@Order(20.0)
			public class TagIdField extends AbstractLongField {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("TagId");
				}

				@Override
				protected boolean getConfiguredVisible() {
					return false;
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
			ITagService service = SERVICES.getService(ITagService.class);
			TagFormData formData = new TagFormData();
			exportFormData(formData);
			service.updateTag(formData);
		}
	}

	public class NewHandler extends AbstractFormHandler {

		@Override
		public void execStore() throws ProcessingException {
			ITagService service = SERVICES.getService(ITagService.class);
			TagFormData formData = new TagFormData();
			exportFormData(formData);
			formData = service.create(formData);
		}
	}
}
