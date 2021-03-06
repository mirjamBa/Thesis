package de.hsrm.perfunctio.core.shared.services.formdata;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import de.hsrm.perfunctio.core.shared.services.code.DatatypeCodeType;
import de.hsrm.perfunctio.core.shared.services.code.FileTypeCodeType;

/**
 * Data transfer object for the AttributeForm
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class AttributeFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public AttributeFormData() {
	}

	public AttributeIdProperty getAttributeIdProperty() {
		return getPropertyByClass(AttributeIdProperty.class);
	}
	/**
	 * access method for property AttributeId.
	 */
	public Long getAttributeId() {
		return getAttributeIdProperty().getValue();
	}
	/**
	 * access method for property AttributeId.
	 */
	public void setAttributeId(Long attributeId) {
		getAttributeIdProperty().setValue(attributeId);
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
	public ShowInFileTable getShowInFileTable() {
		return getFieldByClass(ShowInFileTable.class);
	}
	public class AttributeIdProperty extends AbstractPropertyData<Long> {
		private static final long serialVersionUID = 1L;

		public AttributeIdProperty() {
		}
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
			ruleMap.put(ValidationRule.MANDATORY, true);
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
			ruleMap.put(ValidationRule.MANDATORY, true);
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
	public static class ShowInFileTable extends AbstractValueFieldData<Boolean> {
		private static final long serialVersionUID = 1L;

		public ShowInFileTable() {
		}
	}
}
