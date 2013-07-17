package de.hsrm.mi.user.client.ui.desktop.outlines.pages;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.osgi.BundleClassDescriptor;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.pages.AbstractExtensiblePageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.security.BasicHierarchyPermission;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.rt.shared.services.common.security.IPermissionService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.user.client.forms.AssignToRoleForm;
import de.hsrm.mi.user.shared.security.ViewPermissionTablePagePermission;
import de.hsrm.thesis.filemanagement.shared.services.IAssignToRoleService;
import de.hsrm.thesis.filemanagement.shared.services.IRolePermissionService;

public class PermissionTablePage
		extends
			AbstractExtensiblePageWithTable<PermissionTablePage.Table> {

	private Long m_roleId;

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Permissions");
	}

	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}

	@Override
	protected void execInitPage() throws ProcessingException {
		setVisibleGranted(ACCESS
				.getLevel(new ViewPermissionTablePagePermission()) > BasicHierarchyPermission.LEVEL_NONE);
	}

	@Override
	protected Object[][] execLoadTableData(SearchFilter filter)
			throws ProcessingException {
		if (getRoleId() == null) {
			ArrayList<String> rows = new ArrayList<String>();
			BundleClassDescriptor[] permissions = SERVICES.getService(
					IPermissionService.class).getAllPermissionClasses();
			for (int i = 0; i < permissions.length; i++) {
				if (permissions[i].getBundleSymbolicName().contains(
						"filemanagement")
						|| permissions[i].getBundleSymbolicName().contains(
								"administration")
						|| permissions[i].getBundleSymbolicName().contains(
								"user")) {
					rows.add(permissions[i].getSimpleClassName());
				}
			}
			Collections.sort(rows);
			Object[][] data = new Object[rows.size()][1];
			for (int i = 0; i < rows.size(); i++) {
				data[i][0] = rows.get(i);
			}
			return data;
		} else {
			return SERVICES.getService(IRolePermissionService.class)
					.getPermissions(getRoleId());
		}
	}

	@Order(10.0)
	public class Table extends AbstractExtensibleTable {

		public PermissionColumn getPermissionColumn() {
			return getColumnSet().getColumnByClass(PermissionColumn.class);
		}

		@Order(10.0)
		public class PermissionColumn extends AbstractStringColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Permission");
			}

			@Override
			protected int getConfiguredWidth() {
				return 250;
			}
		}

		@Order(10.0)
		public class AssignToRoleMenu extends AbstractExtensibleMenu {

			@Override
			protected boolean getConfiguredMultiSelectionAction() {
				return true;
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("AssignToRole");
			}

			@Override
			protected void execAction() throws ProcessingException {
				AssignToRoleForm form = new AssignToRoleForm();
				form.setPermission(getPermissionColumn().getSelectedValues());
				form.startNew();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}

			}
		}

		@Order(20.0)
		public class RemovePermissionFromRoleMenu
				extends
					AbstractExtensibleMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("RemovePermissionFromRole");
			}

			@Override
			protected void execPrepareAction() throws ProcessingException {
				if (getRoleId() == null) {
					setVisible(false);
				}
			}

			@Override
			protected void execAction() throws ProcessingException {
				SERVICES.getService(IAssignToRoleService.class).remove(
						getRoleId(), getPermissionColumn().getSelectedValues());
			}
		}
	}

	@FormData
	public Long getRoleId() {
		return m_roleId;
	}

	@FormData
	public void setRoleId(Long roleId) {
		m_roleId = roleId;
	}

}
