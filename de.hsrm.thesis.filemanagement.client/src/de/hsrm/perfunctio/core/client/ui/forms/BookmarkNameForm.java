package de.hsrm.perfunctio.core.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.perfunctio.core.client.ui.forms.BookmarkNameForm.MainBox.BookmarkNameBox;
import de.hsrm.perfunctio.core.client.ui.forms.BookmarkNameForm.MainBox.BookmarkNameBox.NameField;
import de.hsrm.perfunctio.core.client.ui.forms.BookmarkNameForm.MainBox.CancelButton;
import de.hsrm.perfunctio.core.client.ui.forms.BookmarkNameForm.MainBox.OkButton;
import de.hsrm.perfunctio.core.shared.services.formdata.BookmarkNameFormData;

/**
 * Form for creating a new search bookmark
 * 
 * @author Mirjam Bayatloo
 * 
 */
@FormData(value = BookmarkNameFormData.class, sdkCommand = SdkCommand.CREATE)
public class BookmarkNameForm extends AbstractForm {

	public BookmarkNameForm() throws ProcessingException {
		super();
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("BookmarkName");
	}

	public BookmarkNameBox getBookmarkNameBox() {
		return getFieldByClass(BookmarkNameBox.class);
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
	}

	public void startNew() throws ProcessingException {
		startInternal(new NewHandler());
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public NameField getNameField() {
		return getFieldByClass(NameField.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Order(10.0)
		public class BookmarkNameBox extends AbstractGroupBox {

			@Order(10.0)
			public class NameField extends AbstractStringField {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Name");
				}

				@Override
				protected boolean getConfiguredMandatory() {
					return true;
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
		protected void execStore() throws ProcessingException {

		}
	}
}
