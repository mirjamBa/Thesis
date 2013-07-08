package de.hsrm.thesis.filemanagement.shared.services.formdata;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;
public class RoleFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public RoleFormData() {
	}

	public RoleIdProperty getRoleIdProperty() {
		return getPropertyByClass(RoleIdProperty.class);
	}
	/**
	 * access method for property RoleId.
	 */
	public long getRoleId() {
		return (getRoleIdProperty().getValue() == null)
				? (0L)
				: (getRoleIdProperty().getValue());
	}
	/**
	 * access method for property RoleId.
	 */
	public void setRoleId(long roleId) {
		getRoleIdProperty().setValue(roleId);
	}
	public Name getName() {
		return getFieldByClass(Name.class);
	}
	public class RoleIdProperty extends AbstractPropertyData<Long> {
		private static final long serialVersionUID = 1L;

		public RoleIdProperty() {
		}
	}
	public static class Name extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Name() {
		}

		/**
		 * list of derived validation rules.
		 */
		@Override
		protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
			super.initValidationRules(ruleMap);
			ruleMap.put(ValidationRule.MAX_LENGTH, 4000);
		}
	}
}
