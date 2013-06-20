package de.hsrm.thesis.filemanagement.shared.formdata;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import de.hsrm.thesis.filemanagement.shared.services.lookup.RoleLookupCall;

public class UserFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public UserFormData() {
	}

	public UserIdProperty getUserIdProperty() {
		return getPropertyByClass(UserIdProperty.class);
	}

	/**
	 * access method for property UserId.
	 */
	public Long getUserId() {
		return getUserIdProperty().getValue();
	}

	/**
	 * access method for property UserId.
	 */
	public void setUserId(Long userId) {
		getUserIdProperty().setValue(userId);
	}

	public Password getPassword() {
		return getFieldByClass(Password.class);
	}

	public Role getRole() {
		return getFieldByClass(Role.class);
	}

	public Username getUsername() {
		return getFieldByClass(Username.class);
	}

	public class UserIdProperty extends AbstractPropertyData<Long> {
		private static final long serialVersionUID = 1L;

		public UserIdProperty() {
		}
	}

	public static class Password extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Password() {
		}

		/**
		 * list of derived validation rules.
		 */
		@Override
		protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
			super.initValidationRules(ruleMap);
			ruleMap.put(ValidationRule.MANDATORY, true);
			ruleMap.put(ValidationRule.MAX_LENGTH, 64);
		}
	}

	public static class Role extends AbstractValueFieldData<Long[]> {
		private static final long serialVersionUID = 1L;

		public Role() {
		}

		/**
		 * list of derived validation rules.
		 */
		@Override
		protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
			super.initValidationRules(ruleMap);
			ruleMap.put(ValidationRule.LOOKUP_CALL, RoleLookupCall.class);
		}
	}

	public static class Username extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Username() {
		}

		/**
		 * list of derived validation rules.
		 */
		@Override
		protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
			super.initValidationRules(ruleMap);
			ruleMap.put(ValidationRule.MANDATORY, true);
			ruleMap.put(ValidationRule.MAX_LENGTH, 32);
		}
	}
}
