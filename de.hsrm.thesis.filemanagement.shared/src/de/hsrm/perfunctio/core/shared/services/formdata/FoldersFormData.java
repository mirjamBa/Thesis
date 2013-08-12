package de.hsrm.perfunctio.core.shared.services.formdata;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

/**
 * Data transfer object for the FoldersForm
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class FoldersFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public FoldersFormData() {
	}

	public FolderIdProperty getFolderIdProperty() {
		return getPropertyByClass(FolderIdProperty.class);
	}
	/**
	 * access method for property FolderId.
	 */
	public Long getFolderId() {
		return getFolderIdProperty().getValue();
	}
	/**
	 * access method for property FolderId.
	 */
	public void setFolderId(Long folderId) {
		getFolderIdProperty().setValue(folderId);
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
	public Name getName() {
		return getFieldByClass(Name.class);
	}
	public class FolderIdProperty extends AbstractPropertyData<Long> {
		private static final long serialVersionUID = 1L;

		public FolderIdProperty() {
		}
	}
	public class ParentFolderIdProperty extends AbstractPropertyData<Long> {
		private static final long serialVersionUID = 1L;

		public ParentFolderIdProperty() {
		}
	}
	public static class Name extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Name() {
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
