package de.hsrm.thesis.filemanagement.shared.formdata;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldData;

import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.lookup.TagLookupCall;
import de.hsrm.thesis.filemanagement.shared.services.lookup.UserLookupCall;

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

	public Metadata getMetadata() {
		return getFieldByClass(Metadata.class);
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

	public static class Metadata extends AbstractTableFieldData {
		private static final long serialVersionUID = 1L;

		public Metadata() {
		}

		public static final int ATTRIBUT_ID_COLUMN_ID = 0;
		public static final int ATTRIBUTE_COLUMN_ID = 1;
		public static final int VALUE_COLUMN_ID = 2;
		public static final int DATATYPE_COLUMN_ID = 3;

		public void setAttributID(int row, Long attributID) {
			setValueInternal(row, ATTRIBUT_ID_COLUMN_ID, attributID);
		}

		public Long getAttributID(int row) {
			return (Long) getValueInternal(row, ATTRIBUT_ID_COLUMN_ID);
		}

		public void setAttribute(int row, String attribute) {
			setValueInternal(row, ATTRIBUTE_COLUMN_ID, attribute);
		}

		public String getAttribute(int row) {
			return (String) getValueInternal(row, ATTRIBUTE_COLUMN_ID);
		}

		public void setValue(int row, String value) {
			setValueInternal(row, VALUE_COLUMN_ID, value);
		}

		public String getValue(int row) {
			return (String) getValueInternal(row, VALUE_COLUMN_ID);
		}

		public void setDatatype(int row, Long datatype) {
			setValueInternal(row, DATATYPE_COLUMN_ID, datatype);
		}

		public Long getDatatype(int row) {
			return (Long) getValueInternal(row, DATATYPE_COLUMN_ID);
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case ATTRIBUT_ID_COLUMN_ID:
				return getAttributID(row);
			case ATTRIBUTE_COLUMN_ID:
				return getAttribute(row);
			case VALUE_COLUMN_ID:
				return getValue(row);
			case DATATYPE_COLUMN_ID:
				return getDatatype(row);
			default:
				return null;
			}
		}

		@Override
		public void setValueAt(int row, int column, Object value) {
			switch (column) {
			case ATTRIBUT_ID_COLUMN_ID:
				setAttributID(row, (Long) value);
				break;
			case ATTRIBUTE_COLUMN_ID:
				setAttribute(row, (String) value);
				break;
			case VALUE_COLUMN_ID:
				setValue(row, (String) value);
				break;
			case DATATYPE_COLUMN_ID:
				setDatatype(row, (Long) value);
				break;
			}
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
			ruleMap.put(ValidationRule.LOOKUP_CALL, TagLookupCall.class);
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
			ruleMap.put(ValidationRule.LOOKUP_CALL, UserLookupCall.class);
			ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
		}
	}
}
