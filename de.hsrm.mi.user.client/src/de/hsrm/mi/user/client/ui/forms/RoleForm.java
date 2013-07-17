package de.hsrm.mi.user.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBoxFilterBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.IListBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.user.client.ui.forms.RoleForm.MainBox.CancelButton;
import de.hsrm.mi.user.client.ui.forms.RoleForm.MainBox.OkButton;
import de.hsrm.mi.user.client.ui.forms.RoleForm.MainBox.RoleBox;
import de.hsrm.mi.user.client.ui.forms.RoleForm.MainBox.RoleBox.NameField;
import de.hsrm.mi.user.client.ui.forms.RoleForm.MainBox.RoleBox.PermissionsField;
import de.hsrm.mi.user.client.ui.forms.RoleForm.MainBox.RoleBox.PermissionsFilterBox;
import de.hsrm.thesis.filemanagement.shared.services.IRoleProcessService;
import de.hsrm.thesis.filemanagement.shared.services.formdata.RoleFormData;
import de.hsrm.thesis.filemanagement.shared.services.lookup.PermissionsLookupCall;

@FormData(value = RoleFormData.class, sdkCommand = SdkCommand.CREATE)
public class RoleForm extends AbstractForm {

	private long m_roleId;

	public RoleForm() throws ProcessingException {
		super();
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Role");
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

	public NameField getNameField() {
		return getFieldByClass(NameField.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	public PermissionsFilterBox getPermissionsBox() {
		return getFieldByClass(PermissionsFilterBox.class);
	}

	public PermissionsField getPermissionsField() {
		return getFieldByClass(PermissionsField.class);
	}

	public RoleBox getRoleBox() {
		return getFieldByClass(RoleBox.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Order(20.0)
		public class RoleBox extends AbstractGroupBox {
			
			public PermissionsField getPermissionField(){
				return getFieldByClass(PermissionsField.class);
			}

			@Order(10.0)
			public class NameField extends AbstractStringField {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Name");
				}
			}

			@Order(20.0)
			public class PermissionsField extends AbstractListBox<String> {

				@Override
				protected int getConfiguredGridH() {
					return 7;
				}

				@Override
				protected int getConfiguredGridW() {
					return 2;
				}

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Permissions");
				}

				@Override
				protected Class<? extends LookupCall> getConfiguredLookupCall() {
					return PermissionsLookupCall.class;
				}
				
			}

			@Order(30.0)
			public class PermissionsFilterBox extends AbstractListBoxFilterBox {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Permissions");
				}

				@Override
				protected IListBox<String> getListBox() {
					return getPermissionsField();
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
		protected void execStore() throws ProcessingException {
			RoleFormData formData = new RoleFormData();
			exportFormData(formData);
			formData = SERVICES.getService(IRoleProcessService.class).update(
					formData);
		}

	}

	public class NewHandler extends AbstractFormHandler {

		@Override
		public void execStore() throws ProcessingException {
			IRoleProcessService service = SERVICES
					.getService(IRoleProcessService.class);
			RoleFormData formData = new RoleFormData();
			exportFormData(formData);
			formData = service.create(formData);
		}
	}

	@FormData
	public long getRoleId() {
		return m_roleId;
	}

	@FormData
	public void setRoleId(long roleId) {
		m_roleId = roleId;
	}
}
