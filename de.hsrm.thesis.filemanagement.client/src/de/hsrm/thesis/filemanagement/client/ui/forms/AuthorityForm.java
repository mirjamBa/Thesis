package de.hsrm.thesis.filemanagement.client.ui.forms;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.ui.forms.AuthorityForm.MainBox.AuthorityBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.AuthorityForm.MainBox.CancelButton;
import de.hsrm.thesis.filemanagement.client.ui.forms.AuthorityForm.MainBox.OkButton;
import de.hsrm.thesis.filemanagement.client.ui.forms.AuthorityForm.MainBox.AuthorityBox.UserRolesField;
import de.hsrm.thesis.filemanagement.shared.AuthorityFormData;
import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.IFolderService;

@FormData(value = AuthorityFormData.class, sdkCommand = SdkCommand.CREATE)
public class AuthorityForm extends AbstractForm {

	private Long m_folderId;
	private Long[] m_oldRoleIds;

	public AuthorityForm(Long folderId, Long[] oldRoleIds)
			throws ProcessingException {
		super();
		m_folderId = folderId;
		m_oldRoleIds = oldRoleIds;
		getUserRolesField().setValue(m_oldRoleIds);
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Authority");
	}

	public AuthorityBox getAuthorityBox() {
		return getFieldByClass(AuthorityBox.class);
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

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	public UserRolesField getUserRolesField() {
		return getFieldByClass(UserRolesField.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Order(10.0)
		public class AuthorityBox extends AbstractGroupBox {

			@Order(10.0)
			public class UserRolesField extends RolesField {
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
			AuthorityFormData formData = new AuthorityFormData();
			exportFormData(formData);

			Long[] selectedRoles = formData.getUserRoles().getValue();

			// free folder
			SERVICES.getService(IFileService.class)
					.updateRoleFileAndFolderPermission(getFolderId(),
							selectedRoles);

			// extract new roles
			if (selectedRoles != null) {
				List<Long> newRoles = new ArrayList<Long>(
						Arrays.asList(selectedRoles));
				newRoles.removeAll(Arrays.asList(getOldRoleIds()));
				if (newRoles.size() > 0) {
					// add new roles to child folders and files
					SERVICES.getService(IFolderService.class)
							.addFreeingToChildFolderAndFiles(getFolderId(),
									newRoles.toArray(new Long[newRoles.size()]));
				}
			}

			// extract deleted roles
			List<Long> removedRoles = new ArrayList<Long>(
					Arrays.asList(getOldRoleIds()));
			if (selectedRoles != null) {
				removedRoles.removeAll(Arrays.asList(selectedRoles));
			}
			if (removedRoles.size() > 0) {
				// remove roles from child folders and files
				SERVICES.getService(IFolderService.class)
						.removeFreeingFromChildFolderAndFiles(
								getFolderId(),
								removedRoles.toArray(new Long[removedRoles
										.size()]));
			}

		}
	}

	@FormData
	public Long getFolderId() {
		return m_folderId;
	}

	@FormData
	public void setFolderId(Long folderId) {
		m_folderId = folderId;
	}

	@FormData
	public Long[] getOldRoleIds() {
		return m_oldRoleIds;
	}

	@FormData
	public void setOldRoleIds(Long[] oldRoleIds) {
		m_oldRoleIds = oldRoleIds;
	}
}
