package de.hsrm.thesis.filemanagement.shared.forms;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
public class BookmarkNameFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public BookmarkNameFormData() {
	}

	public Name getName() {
		return getFieldByClass(Name.class);
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
