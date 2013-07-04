package de.hsrm.thesis.filemanagement.client.forms;

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
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.forms.FoldersForm.MainBox.CancelButton;
import de.hsrm.thesis.filemanagement.client.forms.FoldersForm.MainBox.FoldersBox;
import de.hsrm.thesis.filemanagement.client.forms.FoldersForm.MainBox.FoldersBox.NameField;
import de.hsrm.thesis.filemanagement.client.forms.FoldersForm.MainBox.OkButton;
import de.hsrm.thesis.filemanagement.shared.forms.FoldersFormData;
import de.hsrm.thesis.filemanagement.shared.services.IFolderService;

@FormData(value = FoldersFormData.class, sdkCommand = SdkCommand.CREATE)
public class FoldersForm extends AbstractForm {

	private Long m_parentFolderId;
	private Long m_folderId;

	public FoldersForm() throws ProcessingException {
		super();
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Folders");
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

	public FoldersBox getFoldersBox() {
		return getFieldByClass(FoldersBox.class);
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
		public class FoldersBox extends AbstractGroupBox {

			@Override
			protected String getConfiguredLabel() {
				return TEXTS.get("Folders");
			}

			@Order(10.0)
			public class NameField extends AbstractStringField {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Name");
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

	public class ModifyHandler extends AbstractFormHandler {
		
		@Override
		protected void execStore() throws ProcessingException {
			FoldersFormData formData = new FoldersFormData();
			exportFormData(formData);
			SERVICES.getService(IFolderService.class).updateFolder(formData);
		}
	}

	public class NewHandler extends AbstractFormHandler {
		
		@Override
		protected void execStore() throws ProcessingException {
			FoldersFormData formData = new FoldersFormData();
			exportFormData(formData);
			SERVICES.getService(IFolderService.class).createFolder(formData, getParentFolderId());
		}
	}

	@FormData
	public Long getParentFolderId() {
		return m_parentFolderId;
	}

	@FormData
	public void setParentFolderId(Long parentFolderId) {
		m_parentFolderId = parentFolderId;
	}

	@FormData
	public Long getFolderId() {
		return m_folderId;
	}

	@FormData
	public void setFolderId(Long folderId) {
		m_folderId = folderId;
	}
}
