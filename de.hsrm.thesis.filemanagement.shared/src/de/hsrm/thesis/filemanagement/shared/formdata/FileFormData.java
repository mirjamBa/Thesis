package de.hsrm.thesis.filemanagement.shared.formdata;

import java.util.Date;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import de.hsrm.thesis.filemanagement.shared.files.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;

public class FileFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public FileFormData() {
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

	public FileNrProperty getFileNrProperty() {
		return getPropertyByClass(FileNrProperty.class);
	}

	/**
	 * access method for property FileNr.
	 */
	public Long getFileNr() {
		return getFileNrProperty().getValue();
	}

	/**
	 * access method for property FileNr.
	 */
	public void setFileNr(Long fileNr) {
		getFileNrProperty().setValue(fileNr);
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

	public Attribute getAttribute() {
		return getFieldByClass(Attribute.class);
	}

	public AvailableTagsBox getAvailableTagsBox() {
		return getFieldByClass(AvailableTagsBox.class);
	}

	public Contributor getContributor() {
		return getFieldByClass(Contributor.class);
	}

	public Coverage getCoverage() {
		return getFieldByClass(Coverage.class);
	}

	public CreationDate getCreationDate() {
		return getFieldByClass(CreationDate.class);
	}

	public Creator getCreator() {
		return getFieldByClass(Creator.class);
	}

	public DateMetadata getDateMetadata() {
		return getFieldByClass(DateMetadata.class);
	}

	public Description getDescription() {
		return getFieldByClass(Description.class);
	}

	public FileExtension getFileExtension() {
		return getFieldByClass(FileExtension.class);
	}

	public Filesize getFilesize() {
		return getFieldByClass(Filesize.class);
	}

	public Format getFormat() {
		return getFieldByClass(Format.class);
	}

	public Identifier getIdentifier() {
		return getFieldByClass(Identifier.class);
	}

	public Language getLanguage() {
		return getFieldByClass(Language.class);
	}

	public NewTag getNewTag() {
		return getFieldByClass(NewTag.class);
	}

	public Publisher getPublisher() {
		return getFieldByClass(Publisher.class);
	}

	public Relation getRelation() {
		return getFieldByClass(Relation.class);
	}

	public Rights getRights() {
		return getFieldByClass(Rights.class);
	}

	public Roles getRoles() {
		return getFieldByClass(Roles.class);
	}

	public Source0 getSource0() {
		return getFieldByClass(Source0.class);
	}

	public Subject0 getSubject0() {
		return getFieldByClass(Subject0.class);
	}

	public Title getTitle() {
		return getFieldByClass(Title.class);
	}

	public Type getType() {
		return getFieldByClass(Type.class);
	}

	public Typist getTypist() {
		return getFieldByClass(Typist.class);
	}

	public class FileDataProperty extends AbstractPropertyData<ServerFileData> {
		private static final long serialVersionUID = 1L;

		public FileDataProperty() {
		}
	}

	public class FileNrProperty extends AbstractPropertyData<Long> {
		private static final long serialVersionUID = 1L;

		public FileNrProperty() {
		}
	}

	public class FiletypeNrProperty extends AbstractPropertyData<Long> {
		private static final long serialVersionUID = 1L;

		public FiletypeNrProperty() {
		}
	}

	public static class Attribute extends AbstractTableFieldData {
		private static final long serialVersionUID = 1L;

		public Attribute() {
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

	public static class AvailableTagsBox extends AbstractValueFieldData<Long[]> {
		private static final long serialVersionUID = 1L;

		public AvailableTagsBox() {
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
			 * ui.forms.FileForm.MainBox.File0Box.TagBox.AvailableTagsBox #
			 * getConfiguredLookupCall
			 */
		}
	}

	public static class Contributor extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Contributor() {
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

	public static class Coverage extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Coverage() {
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

	public static class CreationDate extends AbstractValueFieldData<Date> {
		private static final long serialVersionUID = 1L;

		public CreationDate() {
		}
	}

	public static class Creator extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Creator() {
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

	public static class DateMetadata extends AbstractValueFieldData<Date> {
		private static final long serialVersionUID = 1L;

		public DateMetadata() {
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

	public static class FileExtension extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public FileExtension() {
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

	public static class Filesize extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Filesize() {
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

	public static class Format extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Format() {
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

	public static class Identifier extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Identifier() {
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

	public static class Language extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Language() {
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

	public static class NewTag extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public NewTag() {
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

	public static class Publisher extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Publisher() {
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

	public static class Relation extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Relation() {
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

	public static class Rights extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Rights() {
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

	public static class Roles extends AbstractValueFieldData<Long[]> {
		private static final long serialVersionUID = 1L;

		public Roles() {
		}

		/**
		 * list of derived validation rules.
		 */
		@Override
		protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
			super.initValidationRules(ruleMap);
			/**
			 * XXX not processed ValidationRule(lookupCall)
			 * 'RoleLookupCall.class' is not accessible from here.
			 * generatedSourceCode: null at
			 * de.hsrm.thesis.filemanagement.client.
			 * ui.forms.FileForm.MainBox.File0Box.AuthorityBox.RolesField #
			 * getConfiguredLookupCall
			 */
		}
	}

	public static class Source0 extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Source0() {
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

	public static class Subject0 extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Subject0() {
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

	public static class Title extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Title() {
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

	public static class Type extends AbstractValueFieldData<Long> {
		private static final long serialVersionUID = 1L;

		public Type() {
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
			 * ui.forms.FileForm.MainBox.File0Box.MetadataBox.TypistField #
			 * getConfiguredLookupCall
			 */
			ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
		}
	}
}
