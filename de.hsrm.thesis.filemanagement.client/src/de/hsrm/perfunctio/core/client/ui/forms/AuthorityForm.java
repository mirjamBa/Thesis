package de.hsrm.perfunctio.core.client.ui.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.client.ui.forms.AuthorityForm.MainBox.AuthorityBox;
import de.hsrm.perfunctio.core.client.ui.forms.AuthorityForm.MainBox.AuthorityBox.PermTableField;
import de.hsrm.perfunctio.core.client.ui.forms.AuthorityForm.MainBox.AuthorityBox.UserRolesField;
import de.hsrm.perfunctio.core.client.ui.forms.AuthorityForm.MainBox.CancelButton;
import de.hsrm.perfunctio.core.client.ui.forms.AuthorityForm.MainBox.OkButton;
import de.hsrm.perfunctio.core.client.util.RolePermissionManager;
import de.hsrm.perfunctio.core.shared.services.IFileAndFolderFreeingService;
import de.hsrm.perfunctio.core.shared.services.formdata.AuthorityFormData;
import de.hsrm.perfunctio.core.shared.utility.RolePermissionUtility;

/**
 * Form to manage file or folder freeings
 * 
 * @author Mirjam Bayatloo
 * 
 */
@FormData(value = AuthorityFormData.class, sdkCommand = SdkCommand.USE)
public class AuthorityForm extends AbstractForm {

	private Long m_folderId;
	private Long[] m_oldRoleIds;
	private RolePermissionManager m_manager;
	private Map<Long, ArrayList<String>> m_oldPermissions;

	public AuthorityForm(Long folderId,
			Map<Long, ArrayList<String>> rolePermission)
			throws ProcessingException {
		super();
		m_folderId = folderId;
		m_oldPermissions = rolePermission;
		m_oldRoleIds = rolePermission.keySet().toArray(
				new Long[rolePermission.keySet().size()]);
		m_manager = new RolePermissionManager();

		getUserRolesField().setValue(m_oldRoleIds);
	}

	private Map<Long, Map<String, Boolean>> buildRoleMap(
			Map<Long, ArrayList<String>> rolePermissions) {
		Map<Long, Map<String, Boolean>> map = new HashMap<Long, Map<String, Boolean>>();

		// get menu names mapped to their visible permission
		Map<String, String> menuPermissionMapping = getPermissionsField()
				.getTable().getMenuPermissionMapping();

		for (Long roleId : rolePermissions.keySet()) {
			Map<String, Boolean> menus = new HashMap<String, Boolean>();
			// get checked permissions for the roleId
			ArrayList<String> perm = rolePermissions.get(roleId);

			for (String menu : menuPermissionMapping.keySet()) {
				if (perm.contains(menuPermissionMapping.get(menu))) {
					// menu checked
					menus.put(menu, true);
				} else {
					// menu unchecked
					menus.put(menu, false);
				}
			}
			// add checked and unchecked menus to role
			map.put(roleId, menus);
		}

		return map;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Authority");
	}

	public AuthorityBox getAuthorityBox() {
		return getFieldByClass(AuthorityBox.class);
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
	}

	public void startNew() throws ProcessingException {
		startInternal(new NewHandler());
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	public PermissionTableField getPermissionTableField() {
		return getFieldByClass(PermissionTableField.class);
	}

	public UserRolesField getUserRolesField() {
		return getFieldByClass(UserRolesField.class);
	}

	public PermTableField getPermissionsField() {
		return getFieldByClass(PermTableField.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Order(10.0)
		public class AuthorityBox extends AbstractGroupBox {

			@Order(10.0)
			public class UserRolesField extends RolesField {

				@Override
				protected void execInitField() throws ProcessingException {
					for (ITableRow row : getTable().getRows()) {
						if (row.isChecked()) {
							// select first checked row
							getTable().selectRow(row);
							// save current roleId
							m_manager
									.setCurrentRole((Long) row.getCellValue(0));
							break;
						}
					}
				}
			}

			@Order(20.0)
			public class PermTableField extends PermissionTableField {

				@Override
				protected void execInitField() throws ProcessingException {
					m_manager
							.setPermissionState(buildRoleMap(m_oldPermissions));
					// set permission values for the selected role
					getPermissionsField().getTable()
							.setTableData(
									m_manager.getPermissions(m_manager
											.getCurrentRole()));
				}

				@SuppressWarnings("rawtypes")
				@Override
				protected Class<? extends IValueField> getConfiguredMasterField() {
					return UserRolesField.class;
				}

				@Override
				protected boolean getConfiguredMasterRequired() {
					return true;
				}

				@Override
				protected void execChangedMasterValue(Object newMasterValue)
						throws ProcessingException {
					updateRolePermissions();
				}
			}

		}

		@Order(20.0)
		public class OkButton extends AbstractOkButton {
		}

		@Order(30.0)
		public class CancelButton extends AbstractCancelButton {
		}
	}

	private void updateRolePermissions() throws ProcessingException {
		if (m_manager != null) {
			// get selected permissions
			Map<String, Boolean> perms = new HashMap<String, Boolean>();
			ITableRow[] rows = getPermissionsField().getTable().getRows();
			for (ITableRow row : rows) {
				String permission = (String) row.getCellValue(0);
				Boolean checked = (Boolean) row.getCellValue(1);
				perms.put(permission, checked);
			}

			// save permissions with old master
			if (m_manager.getCurrentRole() != null) {
				m_manager.update(m_manager.getCurrentRole(), perms);
			}

			// save new master value (long)
			ITableRow row = getUserRolesField().getTable().getSelectedRow();
			if (row != null) {
				m_manager.setCurrentRole((Long) row.getCellValue(0));
			}

			// set selected checkboxes for new master value
			Map<String, Boolean> newPerms = m_manager.getPermissions(m_manager
					.getCurrentRole());
			getPermissionsField().getTable().setTableData(newPerms);
		}
	}

	public class NewHandler extends AbstractFormHandler {

		@Override
		protected void execStore() throws ProcessingException {
			AuthorityFormData formData = new AuthorityFormData();
			exportFormData(formData);

			Long[] selectedRoles = formData.getUserRoles().getValue();

			Map<String, String> menuPermissionMapping = new HashMap<String, String>();
			for (ITableRow row : getPermissionsField().getTable().getRows()) {
				menuPermissionMapping.put((String) row.getCellValue(0),
						(String) row.getCellValue(2));
			}

			// save last permission values
			updateRolePermissions();

			m_manager.setMenuPermissionMapping(menuPermissionMapping);
			Map<Long, ArrayList<String>> mapping = m_manager
					.getPermissionsForSelectedRoles(selectedRoles);

			// free folder
			SERVICES.getService(IFileAndFolderFreeingService.class)
					.updateRoleFileAndFolderPermission(getFolderId(),
							selectedRoles, mapping);

			// extract new roles
			Map<Long, ArrayList<String>> newPermissions = RolePermissionUtility
					.getRolePermissionDif(m_oldPermissions, mapping);

			if (newPermissions.size() > 0) {
				// add new roles to child folders and files
				SERVICES.getService(IFileAndFolderFreeingService.class)
						.addFreeingToChildFolderAndFiles(
								getFolderId(),
								newPermissions.keySet()
										.toArray(
												new Long[newPermissions
														.keySet().size()]),
								newPermissions);
			}

			// extract deleted roles
			Map<Long, ArrayList<String>> deletePermissions = RolePermissionUtility
					.getRolePermissionDif(mapping, m_oldPermissions);
			if (deletePermissions.size() > 0) {
				SERVICES.getService(IFileAndFolderFreeingService.class)
						.removeFreeingFromChildFolderAndFiles(getFolderId(),
								deletePermissions);
			}

		}
	}

	@FormData
	public Long getFolderId() {
		return m_folderId;
	}

	@FormData
	public void setFolderId(Long folderId) {
		m_folderId = folderId;
	}

	@FormData
	public Long[] getOldRoleIds() {
		return m_oldRoleIds;
	}

	@FormData
	public void setOldRoleIds(Long[] oldRoleIds) {
		m_oldRoleIds = oldRoleIds;
	}
}
