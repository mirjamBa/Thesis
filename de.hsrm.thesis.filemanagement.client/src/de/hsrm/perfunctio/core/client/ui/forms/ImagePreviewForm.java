package de.hsrm.perfunctio.core.client.ui.forms;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;

import de.hsrm.perfunctio.core.client.ui.forms.ImagePreviewForm.MainBox.ImagePreviewField;

/**
 * Form to preview pictures. Includes a closer timer, set to 5 seconds.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ImagePreviewForm extends AbstractForm {

	public ImagePreviewForm() throws ProcessingException {
		super();
	}

	@Override
	protected int getConfiguredCloseTimer() {
		return 5;
	}

	@Override
	protected int getConfiguredDisplayHint() {
		return DISPLAY_HINT_VIEW;
	}

	@Override
	protected String getConfiguredDisplayViewId() {
		return VIEW_ID_SW;
	}

	public void startNew() throws ProcessingException {
		startInternal(new NewHandler());
	}

	public ImagePreviewField getImagePreviewField() {
		return getFieldByClass(ImagePreviewField.class);
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Order(10.0)
		public class ImagePreviewField extends AbstractImageField {

			@Override
			protected int getConfiguredGridH() {
				return 12;
			}

			@Override
			protected boolean getConfiguredLabelVisible() {
				return false;
			}

			@Override
			protected boolean getConfiguredAutoFit() {
				return true;
			}

		}

	}

	public class NewHandler extends AbstractFormHandler {
	}
}
