package de.hsrm.thesis.filemanagement.shared.services.formdata;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import de.hsrm.thesis.filemanagement.shared.services.lookup.RoleLookupCall;
public class AuthorityFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public AuthorityFormData() {
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
	public OldRoleIdsProperty getOldRoleIdsProperty() {
		return getPropertyByClass(OldRoleIdsProperty.class);
	}
	/**
	 * access method for property OldRoleIds.
	 */
	public Long[] getOldRoleIds() {
		return getOldRoleIdsProperty().getValue();
	}
	/**
	 * access method for property OldRoleIds.
	 */
	public void setOldRoleIds(Long[] oldRoleIds) {
		getOldRoleIdsProperty().setValue(oldRoleIds);
	}
	public UserRoles getUserRoles() {
		return getFieldByClass(UserRoles.class);
	}
	public class FolderIdProperty extends AbstractPropertyData<Long> {
		private static final long serialVersionUID = 1L;

		public FolderIdProperty() {
		}
	}
	public class OldRoleIdsProperty extends AbstractPropertyData<Long[]> {
		private static final long serialVersionUID = 1L;

		public OldRoleIdsProperty() {
		}
	}
	public static class UserRoles extends AbstractValueFieldData<Long[]> {
		private static final long serialVersionUID = 1L;

		public UserRoles() {
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
}
