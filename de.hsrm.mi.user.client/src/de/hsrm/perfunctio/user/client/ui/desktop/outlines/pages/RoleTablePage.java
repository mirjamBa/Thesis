package de.hsrm.perfunctio.user.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.security.BasicHierarchyPermission;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.services.IRolePermissionService;
import de.hsrm.perfunctio.core.shared.services.IRoleProcessService;
import de.hsrm.perfunctio.user.client.ui.forms.RoleForm;
import de.hsrm.perfunctio.user.shared.security.ViewRoleTablePagePermission;

/**
 * TablePage for all available Roles with their Permissions as a child page. You
 * can add, modify and delete roles with this page.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class RoleTablePage extends AbstractPageWithTable<RoleTablePage.Table> {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Role");
	}

	@Override
	protected IPage execCreateChildPage(ITableRow row)
			throws ProcessingException {
		PermissionTablePage childPage = new PermissionTablePage();
		childPage.setRoleId(getTable().getRoleIdColumn().getValue(row));
		return childPage;
	}

	@Override
	protected void execInitPage() throws ProcessingException {
		setVisibleGranted(ACCESS.getLevel(new ViewRoleTablePagePermission()) > BasicHierarchyPermission.LEVEL_NONE);
	}

	@Override
	protected Object[][] execLoadTableData(SearchFilter filter)
			throws ProcessingException {
		return SERVICES.getService(IRoleProcessService.class).getRoles();
	}

	@Order(10.0)
	public class Table extends AbstractExtensibleTable {

		@Override
		protected boolean getConfiguredAutoResizeColumns() {
			return true;
		}

		public RoleIdColumn getRoleIdColumn() {
			return getColumnSet().getColumnByClass(RoleIdColumn.class);
		}

		public RoleColumn getRoleColumn() {
			return getColumnSet().getColumnByClass(RoleColumn.class);
		}

		@Order(10.0)
		public class RoleIdColumn extends AbstractLongColumn {

			@Override
			protected boolean getConfiguredDisplayable() {
				return false;
			}

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("RoleId");
			}

			@Override
			protected boolean getConfiguredVisible() {
				return false;
			}
		}

		@Order(20.0)
		public class RoleColumn extends AbstractStringColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Role");
			}

		}

		@Order(10.0)
		public class NewRoleMenu extends AbstractExtensibleMenu {

			@Override
			protected boolean getConfiguredSingleSelectionAction() {
				return false;
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("NewRole");
			}

			@Override
			protected boolean getConfiguredEmptySpaceAction() {
				return true;
			}

			@Override
			protected void execAction() throws ProcessingException {
				RoleForm form = new RoleForm();
				form.startNew();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(20.0)
		public class DeleteMenu extends AbstractExtensibleMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("DeleteMenu");
			}

			@Override
			protected void execAction() throws ProcessingException {
				SERVICES.getService(IRoleProcessService.class).deleteRoles(
						getRoleIdColumn().getSelectedValues());
				reloadPage();
			}
		}

		@Order(30.0)
		public class ModifyMenu extends AbstractExtensibleMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Modify");
			}

			@Override
			protected void execAction() throws ProcessingException {
				RoleForm form = new RoleForm();
				form.getNameField()
						.setValue(getRoleColumn().getSelectedValue());
				form.setRoleId(getRoleIdColumn().getSelectedValue());

				Object[][] permissions = SERVICES.getService(
						IRolePermissionService.class).getPermissions(
						getRoleIdColumn().getSelectedValue());
				form.getPermissionsField().setValue(
						getPermissionNames(permissions));
				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}
	}

	private String[] getPermissionNames(Object[][] tableData) {
		String[] result = new String[tableData.length];
		for (int i = 0; i < tableData.length; i++) {
			result[i] = (String) tableData[i][0];
		}
		return result;
	}
}
