package de.hsrm.perfunctio.core.shared.services.formdata;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

/**
 * Data transfer object for the TagForm
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class TagFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public TagFormData() {
	}

	public TagId getTagId() {
		return getFieldByClass(TagId.class);
	}
	public TagName getTagName() {
		return getFieldByClass(TagName.class);
	}
	public static class TagId extends AbstractValueFieldData<Long> {
		private static final long serialVersionUID = 1L;

		public TagId() {
		}
	}
	public static class TagName extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public TagName() {
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
