package de.hsrm.mi.user.client.forms;

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
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.user.client.forms.AssignToRoleForm.MainBox.AssignToRoleFormBox;
import de.hsrm.mi.user.client.forms.AssignToRoleForm.MainBox.AssignToRoleFormBox.RoleField;
import de.hsrm.mi.user.client.forms.AssignToRoleForm.MainBox.CancelButton;
import de.hsrm.mi.user.client.forms.AssignToRoleForm.MainBox.OkButton;
import de.hsrm.thesis.filemanagement.shared.formdata.AssignToRoleFormData;
import de.hsrm.thesis.filemanagement.shared.services.IAssignToRoleService;
import de.hsrm.thesis.filemanagement.shared.services.lookup.AllRoleLookupCall;

@FormData(value = AssignToRoleFormData.class, sdkCommand = SdkCommand.CREATE)
public class AssignToRoleForm extends AbstractForm {

	private String[] m_permission;

	public AssignToRoleForm() throws ProcessingException {
		super();
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("AssignToRoleForm");
	}

	public void startNew() throws ProcessingException {
		startInternal(new NewHandler());
	}

	public AssignToRoleFormBox getAssignToRoleFormBox() {
		return getFieldByClass(AssignToRoleFormBox.class);
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	public RoleField getRoleField() {
		return getFieldByClass(RoleField.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Order(10.0)
		public class AssignToRoleFormBox extends AbstractGroupBox {

			@Order(10.0)
			public class RoleField extends AbstractSmartField<Long> {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Role");
				}

				@Override
				protected Class<? extends LookupCall> getConfiguredLookupCall() {
					return AllRoleLookupCall.class;

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
			IAssignToRoleService service = SERVICES.getService(IAssignToRoleService.class);
			AssignToRoleFormData formData = new AssignToRoleFormData();
			exportFormData(formData);
			formData = service.create(formData);
		}
	}

	@FormData
	public String[] getPermission() {
		return m_permission;
	}

	@FormData
	public void setPermission(String[] permission) {
		m_permission = permission;
	}
}
