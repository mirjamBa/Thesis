package de.hsrm.thesis.filemanagement.shared.formdata;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import de.hsrm.thesis.filemanagement.shared.nonFormdataBeans.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.lookup.RoleLookupCall;
import de.hsrm.thesis.filemanagement.shared.services.lookup.TagLookupCall;
import de.hsrm.thesis.filemanagement.shared.services.lookup.UserLookupCall;
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
		return (getFiletypeNrProperty().getValue() == null)
				? (0L)
				: (getFiletypeNrProperty().getValue());
	}
	/**
	 * access method for property FiletypeNr.
	 */
	public void setFiletypeNr(long filetypeNr) {
		getFiletypeNrProperty().setValue(filetypeNr);
	}
	public ParentFolderIdProperty getParentFolderIdProperty() {
		return getPropertyByClass(ParentFolderIdProperty.class);
	}
	/**
	 * access method for property ParentFolderId.
	 */
	public Long getParentFolderId() {
		return getParentFolderIdProperty().getValue();
	}
	/**
	 * access method for property ParentFolderId.
	 */
	public void setParentFolderId(Long parentFolderId) {
		getParentFolderIdProperty().setValue(parentFolderId);
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
	public Date getDate() {
		return getFieldByClass(Date.class);
	}
	public Description getDescription() {
		return getFieldByClass(Description.class);
	}
	public FileExtension getFileExtension() {
		return getFieldByClass(FileExtension.class);
	}
	public FileFormMetadataTable getFileFormMetadataTable() {
		return getFieldByClass(FileFormMetadataTable.class);
	}
	public FileFormRoles getFileFormRoles() {
		return getFieldByClass(FileFormRoles.class);
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
	public Source getSource() {
		return getFieldByClass(Source.class);
	}
	public Subject getSubject() {
		return getFieldByClass(Subject.class);
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
	public class ParentFolderIdProperty extends AbstractPropertyData<Long> {
		private static final long serialVersionUID = 1L;

		public ParentFolderIdProperty() {
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
			ruleMap.put(ValidationRule.LOOKUP_CALL, TagLookupCall.class);
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
	public static class CreationDate
			extends
				AbstractValueFieldData<java.util.Date> {
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
	public static class Date extends AbstractValueFieldData<java.util.Date> {
		private static final long serialVersionUID = 1L;

		public Date() {
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
	public static class FileFormMetadataTable extends MetadataTableFieldData {
		private static final long serialVersionUID = 1L;

		public FileFormMetadataTable() {
			super();
		}
	}
	public static class FileFormRoles extends AbstractValueFieldData<Long[]> {
		private static final long serialVersionUID = 1L;

		public FileFormRoles() {
		}

		/**
		 * list of derived validation rules.
		 */
		@Override
		protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
			super.initValidationRules(ruleMap);
			ruleMap.put(ValidationRule.LOOKUP_CALL, RoleLookupCall.class);
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
	public static class Source extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Source() {
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
	public static class Subject extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Subject() {
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
			ruleMap.put(ValidationRule.LOOKUP_CALL, UserLookupCall.class);
			ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
		}
	}
}
