package de.hsrm.perfunctio.core.shared.services.formdata;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

/**
 * Data transfer object for the BookmarkNameForm
 * 
 * @author Mirjam Bayatloo
 * 
 */
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
			ruleMap.put(ValidationRule.MANDATORY, true);
			ruleMap.put(ValidationRule.MAX_LENGTH, 4000);
		}
	}
}
