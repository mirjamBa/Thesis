package de.hsrm.thesis.filemanagement.shared.formdata;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;

public class FileSearchFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public FileSearchFormData() {
	}

	public FileNrFrom getFileNrFrom() {
		return getFieldByClass(FileNrFrom.class);
	}

	public FileNrTo getFileNrTo() {
		return getFieldByClass(FileNrTo.class);
	}

	public FileType getFileType() {
		return getFieldByClass(FileType.class);
	}

	public GeneralSearch getGeneralSearch() {
		return getFieldByClass(GeneralSearch.class);
	}

	public Tag getTag() {
		return getFieldByClass(Tag.class);
	}

	public Typist getTypist() {
		return getFieldByClass(Typist.class);
	}

	public static class FileNrFrom extends AbstractValueFieldData<Long> {
		private static final long serialVersionUID = 1L;

		public FileNrFrom() {
		}
	}

	public static class FileNrTo extends AbstractValueFieldData<Long> {
		private static final long serialVersionUID = 1L;

		public FileNrTo() {
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

	public static class GeneralSearch extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public GeneralSearch() {
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

	public static class Tag extends AbstractValueFieldData<Long[]> {
		private static final long serialVersionUID = 1L;

		public Tag() {
		}

		/**
		 * list of derived validation rules.
		 */
		@Override
		protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
			super.initValidationRules(ruleMap);
			/**
			 * XXX not processed ValidationRule(lookupCall)
			 * 'TagLookupCall.class' is not accessible from here.
			 * generatedSourceCode: null at
			 * de.hsrm.thesis.filemanagement.client.
			 * ui.forms.FileSearchForm.MainBox.TabBox.TagBox.TagField #
			 * getConfiguredLookupCall
			 */
		}
	}

	public static class Typist extends AbstractValueFieldData<Long> {
		private static final long serialVersionUID = 1L;

		public Typist() {
		}

		/**
		 * list of derived validation rules.
		 */
		@Override
		protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
			super.initValidationRules(ruleMap);
			/**
			 * XXX not processed ValidationRule(lookupCall)
			 * 'UserLookupCall.class' is not accessible from here.
			 * generatedSourceCode: null at
			 * de.hsrm.thesis.filemanagement.client.
			 * ui.forms.FileSearchForm.MainBox.TabBox.FieldBox.TypistField #
			 * getConfiguredLookupCall
			 */
			ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
		}
	}
}
