package de.hsrm.thesis.filemanagement.shared.formdata;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;

public class FiletypeChooserFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public FiletypeChooserFormData() {
	}

	public FileformatProperty getFileformatProperty() {
		return getPropertyByClass(FileformatProperty.class);
	}

	/**
	 * access method for property Fileformat.
	 */
	public String getFileformat() {
		return getFileformatProperty().getValue();
	}

	/**
	 * access method for property Fileformat.
	 */
	public void setFileformat(String fileformat) {
		getFileformatProperty().setValue(fileformat);
	}

	public FiletypeNrProperty getFiletypeNrProperty() {
		return getPropertyByClass(FiletypeNrProperty.class);
	}

	/**
	 * access method for property FiletypeNr.
	 */
	public long getFiletypeNr() {
		return (getFiletypeNrProperty().getValue() == null) ? (0L) : (getFiletypeNrProperty().getValue());
	}

	/**
	 * access method for property FiletypeNr.
	 */
	public void setFiletypeNr(long filetypeNr) {
		getFiletypeNrProperty().setValue(filetypeNr);
	}

	public FileType getFileType() {
		return getFieldByClass(FileType.class);
	}

	public FiletypeChooserForm getFiletypeChooserForm() {
		return getFieldByClass(FiletypeChooserForm.class);
	}

	public class FileformatProperty extends AbstractPropertyData<String> {
		private static final long serialVersionUID = 1L;

		public FileformatProperty() {
		}
	}

	public class FiletypeNrProperty extends AbstractPropertyData<Long> {
		private static final long serialVersionUID = 1L;

		public FiletypeNrProperty() {
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

	public static class FiletypeChooserForm extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public FiletypeChooserForm() {
		}
	}
}
