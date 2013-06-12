package de.hsrm.mi.administration.shared.services.formdata;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

import de.hsrm.mi.administration.shared.services.code.DatatypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;

public class MetadataFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public MetadataFormData() {
	}

	public Datatype getDatatype() {
		return getFieldByClass(Datatype.class);
	}

	public Description getDescription() {
		return getFieldByClass(Description.class);
	}

	public FileType getFileType() {
		return getFieldByClass(FileType.class);
	}

	public static class Datatype extends AbstractValueFieldData<Long> {
		private static final long serialVersionUID = 1L;

		public Datatype() {
		}

		/**
		 * list of derived validation rules.
		 */
		@Override
		protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
			super.initValidationRules(ruleMap);
			ruleMap.put(ValidationRule.CODE_TYPE, DatatypeCodeType.class);
			ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
		}
	}

	public static class Description extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Description() {
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
			ruleMap.put(ValidationRule.CODE_TYPE, FileTypeCodeType.class);
			ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
		}
	}
}
