package de.hsrm.perfunctio.user.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.security.CreateUserPermission;
import de.hsrm.perfunctio.core.shared.security.UpdateUserPermission;
import de.hsrm.perfunctio.core.shared.services.IPasswordProcessService;
import de.hsrm.perfunctio.core.shared.services.IUserProcessService;
import de.hsrm.perfunctio.core.shared.services.formdata.UserFormData;
import de.hsrm.perfunctio.core.shared.services.lookup.RoleLookupCall;
import de.hsrm.perfunctio.user.client.ui.forms.UserForm.MainBox.UserBox.CancelButton;
import de.hsrm.perfunctio.user.client.ui.forms.UserForm.MainBox.UserBox.OkButton;
import de.hsrm.perfunctio.user.client.ui.forms.UserForm.MainBox.UserBox.PasswordField;
import de.hsrm.perfunctio.user.client.ui.forms.UserForm.MainBox.UserBox.RoleField;
import de.hsrm.perfunctio.user.client.ui.forms.UserForm.MainBox.UserBox.UsernameField;

/**
 * Form to create or modify users, based on the UserForm from the Scout-Demo:
 * BahBah Chat
 * 
 * @author BSI Business Systems Integration AG, Mirjam Bayatloo
 * @see <a
 *      href="https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo/blob/d95a1816cc0d362fffa23da7fdab2626962e8467/bahbah/org.eclipse.scout.bahbah.client/src/org/eclipse/scout/bahbah/client/ui/forms/UserForm.java">https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo</a>
 * 
 */
@FormData(value = UserFormData.class, sdkCommand = SdkCommand.CREATE)
public class UserForm extends AbstractForm {

	private Long m_userId;

	public UserForm() throws ProcessingException {
		super();
	}

	@FormData
	public Long getUserId() {
		return m_userId;
	}

	@FormData
	public void setUserId(Long userId) {
		m_userId = userId;
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

	public PasswordField getPasswordField() {
		return getFieldByClass(PasswordField.class);
	}

	public RoleField getRoleField() {
		return getFieldByClass(RoleField.class);
	}

	public UsernameField getUsernameField() {
		return getFieldByClass(UsernameField.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Override
		protected int getConfiguredGridColumnCount() {
			return 1;
		}

		@Order(10.0)
		public class UserBox extends AbstractGroupBox {

			@Override
			protected String getConfiguredLabel() {
				return TEXTS.get("User");
			}

			@Order(10.0)
			public class UsernameField extends AbstractStringField {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Username");
				}

				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 32;
				}

				@Override
				protected String execValidateValue(String rawValue)
						throws ProcessingException {
					SERVICES.getService(IUserProcessService.class)
							.checkUsername(rawValue);
					if (!rawValue.equals(rawValue.toLowerCase())) {
						throw new VetoException(TEXTS.get("VETOUsername"));
					}
					return rawValue;
				}
			}

			@Order(20.0)
			public class PasswordField extends AbstractStringField {

				@Override
				protected boolean getConfiguredInputMasked() {
					return true;
				}

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Password");
				}

				@Override
				protected boolean getConfiguredMandatory() {
					return true;
				}

				@Override
				protected int getConfiguredMaxLength() {
					return 64;
				}

				@Override
				protected String execValidateValue(String rawValue)
						throws ProcessingException {
					SERVICES.getService(IPasswordProcessService.class)
							.checkPassword(rawValue);
					return rawValue;
				}
			}

			@Order(30.0)
			public class RoleField extends AbstractListBox<Long> {

				@Override
				protected int getConfiguredGridH() {
					return 5;
				}

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Role");
				}

				@Override
				protected Class<? extends LookupCall> getConfiguredLookupCall() {
					return RoleLookupCall.class;
				}

				@Override
				protected void execPrepareLookup(LookupCall call)
						throws ProcessingException {
					((RoleLookupCall) call).setUserId(SERVICES.getService(
							IUserProcessService.class).getCurrentUserId());
				}

			}

			@Order(50.0)
			public class OkButton extends AbstractOkButton {
			}

			@Order(60.0)
			public class CancelButton extends AbstractCancelButton {
			}
		}
	}

	public class ModifyHandler extends AbstractFormHandler {
		@Override
		protected void execLoad() throws ProcessingException {
			setEnabledPermission(new UpdateUserPermission());
			getPasswordField().setVisibleGranted(false);
			getPasswordField().setMandatory(false);
		}

		@Override
		protected void execStore() throws ProcessingException {
			UserFormData formData = new UserFormData();
			exportFormData(formData);
			SERVICES.getService(IUserProcessService.class).updateUser(formData);
		}
	}

	public class NewHandler extends AbstractFormHandler {

		@Override
		protected void execLoad() throws ProcessingException {
			setEnabledPermission(new CreateUserPermission());
		}

		@Override
		protected void execStore() throws ProcessingException {
			UserFormData formData = new UserFormData();
			exportFormData(formData);
			SERVICES.getService(IUserProcessService.class).createUser(formData);
		}
	}
}
