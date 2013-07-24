package de.hsrm.perfunctio.core.shared.services.formdata;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import de.hsrm.perfunctio.core.shared.services.lookup.AllRoleLookupCall;
public class AssignToRoleFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public AssignToRoleFormData() {
	}

	public PermissionProperty getPermissionProperty() {
		return getPropertyByClass(PermissionProperty.class);
	}
	/**
	 * access method for property Permission.
	 */
	public String[] getPermission() {
		return getPermissionProperty().getValue();
	}
	/**
	 * access method for property Permission.
	 */
	public void setPermission(String[] permission) {
		getPermissionProperty().setValue(permission);
	}
	public Role getRole() {
		return getFieldByClass(Role.class);
	}
	public class PermissionProperty extends AbstractPropertyData<String[]> {
		private static final long serialVersionUID = 1L;

		public PermissionProperty() {
		}
	}
	public static class Role extends AbstractValueFieldData<Long> {
		private static final long serialVersionUID = 1L;

		public Role() {
		}

		/**
		 * list of derived validation rules.
		 */
		@Override
		protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
			super.initValidationRules(ruleMap);
			ruleMap.put(ValidationRule.LOOKUP_CALL, AllRoleLookupCall.class);
			ruleMap.put(ValidationRule.MANDATORY, true);
			ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
		}
	}
}
