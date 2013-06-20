package de.hsrm.thesis.filemanagement.shared.services.formdata;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;

public class VersionFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public VersionFormData() {
	}

	public FileType getFileType() {
		return getFieldByClass(FileType.class);
	}

	public Version getVersion() {
		return getFieldByClass(Version.class);
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

	public static class Version extends AbstractValueFieldData<Boolean> {
		private static final long serialVersionUID = 1L;

		public Version() {
		}
	}
}
