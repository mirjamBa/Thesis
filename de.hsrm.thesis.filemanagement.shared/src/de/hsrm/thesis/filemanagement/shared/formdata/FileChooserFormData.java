package de.hsrm.thesis.filemanagement.shared.formdata;
import java.util.Map;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import de.hsrm.thesis.filemanagement.shared.nonFormdataBeans.ServerFileData;
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
	public ServerFileData getFileData() {
		return getFileDataProperty().getValue();
	}
	/**
	 * access method for property FileData.
	 */
	public void setFileData(ServerFileData fileData) {
		getFileDataProperty().setValue(fileData);
	}
	public MetaValuesProperty getMetaValuesProperty() {
		return getPropertyByClass(MetaValuesProperty.class);
	}
	/**
	 * access method for property MetaValues.
	 */
	public Map<String, String> getMetaValues() {
		return getMetaValuesProperty().getValue();
	}
	/**
	 * access method for property MetaValues.
	 */
	public void setMetaValues(Map<String, String> metaValues) {
		getMetaValuesProperty().setValue(metaValues);
	}
	public File getFile() {
		return getFieldByClass(File.class);
	}
	public class FileDataProperty extends AbstractPropertyData<ServerFileData> {
		private static final long serialVersionUID = 1L;

		public FileDataProperty() {
		}
	}
	public class MetaValuesProperty
			extends
				AbstractPropertyData<Map<String, String>> {
		private static final long serialVersionUID = 1L;

		public MetaValuesProperty() {
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
