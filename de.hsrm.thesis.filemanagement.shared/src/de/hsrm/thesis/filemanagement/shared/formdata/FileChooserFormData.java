package de.hsrm.thesis.filemanagement.shared.formdata;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

public class FileChooserFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public FileChooserFormData() {
	}

	public FileDataProperty getFileDataProperty() {
		return getPropertyByClass(FileDataProperty.class);
	}

	/**
	 * access method for property FileData.
	 */
	public Object getFileData() {
		return getFileDataProperty().getValue();
	}

	/**
	 * access method for property FileData.
	 */
	public void setFileData(Object fileData) {
		getFileDataProperty().setValue(fileData);
	}

	public File getFile() {
		return getFieldByClass(File.class);
	}

	public class FileDataProperty extends AbstractPropertyData<Object> {
		private static final long serialVersionUID = 1L;

		public FileDataProperty() {
		}
	}

	public static class File extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public File() {
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
