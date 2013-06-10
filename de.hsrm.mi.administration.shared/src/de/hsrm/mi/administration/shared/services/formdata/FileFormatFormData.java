package de.hsrm.mi.administration.shared.services.formdata;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import de.hsrm.mi.administration.shared.services.lookup.FiletypeLookupCall;

public class FileFormatFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public FileFormatFormData() {
	}

	public IdProperty getIdProperty() {
		return getPropertyByClass(IdProperty.class);
	}

	/**
	 * access method for property Id.
	 */
	public Long getId() {
		return getIdProperty().getValue();
	}

	/**
	 * access method for property Id.
	 */
	public void setId(Long id) {
		getIdProperty().setValue(id);
	}

	public FileFormat getFileFormat() {
		return getFieldByClass(FileFormat.class);
	}

	public FileType getFileType() {
		return getFieldByClass(FileType.class);
	}

	public class IdProperty extends AbstractPropertyData<Long> {
		private static final long serialVersionUID = 1L;

		public IdProperty() {
		}
	}

	public static class FileFormat extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public FileFormat() {
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

	public static class FileType extends AbstractValueFieldData<Long> {
		private static final long serialVersionUID = 1L;

		public FileType() {
		}

		/**
		 * list of derived validation rules.
		 */
		@Override
		protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
			super.initValidationRules(ruleMap);
			ruleMap.put(ValidationRule.LOOKUP_CALL, FiletypeLookupCall.class);
			ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
		}
	}
}
