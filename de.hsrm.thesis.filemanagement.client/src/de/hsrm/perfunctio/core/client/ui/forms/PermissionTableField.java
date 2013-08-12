package de.hsrm.perfunctio.core.client.ui.forms;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.perfunctio.core.client.ui.IPermissionGrantedMenu;
import de.hsrm.perfunctio.core.client.ui.desktop.outlines.pages.FilesAndFoldersTablePage;
import de.hsrm.perfunctio.core.shared.security.ReadFilePermission;

/**
 * Table for displaying all available menus from the FilesAndFoldersTablePage
 * and their visible permissions. Includes a checkable column for selecting
 * menu-permissions.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class PermissionTableField
		extends
			AbstractTableField<PermissionTableField.Table> {

	@Override
	protected int getConfiguredGridH() {
		return 8;
	}

	@Override
	protected String getConfiguredLabel() {
		return TEXTS.get("Permissions");
	}

	@Order(10.0)
	public class Table extends AbstractExtensibleTable {

		@Override
		protected boolean getConfiguredAutoResizeColumns() {
			return true;
		}

		@Override
		protected void execInitTable() throws ProcessingException {
			setTableData(null);
		}

		protected Map<String, String> getMenuPermissionMapping() {
			Map<String, String> menuPermissionMapping = new HashMap<String, String>();
			for (ITableRow row : getTable().getRows()) {
				menuPermissionMapping.put((String) row.getCellValue(0),
						(String) row.getCellValue(2));
			}
			return menuPermissionMapping;
		}

		protected Map<String, String> getPermissionMenuMapping() {
			Map<String, String> permissionMenuMapping = new HashMap<String, String>();
			for (ITableRow row : getTable().getRows()) {
				permissionMenuMapping.put((String) row.getCellValue(2),
						(String) row.getCellValue(0));
			}
			return permissionMenuMapping;
		}

		protected void setTableData(Map<String, Boolean> checkedPermissions)
				throws ProcessingException {
			IMenu[] menus = new FilesAndFoldersTablePage().getMenuArray();
			deleteAllRows();
			for (IMenu menu : menus) {
				ITableRow newRow = createRow();
				getMenuNameColumn().setValue(newRow, menu.getText());
				if (checkedPermissions != null
						&& checkedPermissions.containsKey(menu.getText())) {
					getOkColumn().setValue(newRow,
							checkedPermissions.get(menu.getText()));
				} else {
					getOkColumn().setValue(newRow, false);
				}
				if (menu instanceof IPermissionGrantedMenu) {
					getPermissionColumn().setValue(
							newRow,
							((IPermissionGrantedMenu) menu)
									.getVisiblePermission() + "Permission");
				}
				addRow(newRow);
			}

			// add read permission
			ITableRow row = createRow();
			getMenuNameColumn().setValue(row, TEXTS.get("ReadPermission"));
			getOkColumn().setValue(row, true);
			getPermissionColumn().setValue(row,
					new ReadFilePermission().getName());
			row.setEnabled(false);
			row.setTooltipText(TEXTS.get("PermissionTableToolTip"));
			addRow(row);
		}

		public OkColumn getOkColumn() {
			return getColumnSet().getColumnByClass(OkColumn.class);
		}

		public MenuNameColumn getMenuNameColumn() {
			return getColumnSet().getColumnByClass(MenuNameColumn.class);
		}

		public PermissionColumn getPermissionColumn() {
			return getColumnSet().getColumnByClass(PermissionColumn.class);
		}

		@Order(10.0)
		public class MenuNameColumn extends AbstractStringColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Permission");
			}
		}

		@Order(20.0)
		public class OkColumn extends AbstractBooleanColumn {

			@Override
			protected boolean getConfiguredEditable() {
				return true;
			}

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Ok");
			}

			@Override
			protected IFormField execPrepareEdit(ITableRow row)
					throws ProcessingException {
				IFormField field = new AbstractBooleanField() {
				};

				((AbstractBooleanField) field).setValue((Boolean) row
						.getCellValue(1));
				return field;
			}

			@Override
			protected void execCompleteEdit(ITableRow row,
					IFormField editingField) throws ProcessingException {
				boolean ok = ((AbstractBooleanField) editingField).getValue();

				getOkColumn().setValue(row, ok);

			}
		}

		@Order(30.0)
		public class PermissionColumn extends AbstractStringColumn {

			@Override
			protected boolean getConfiguredVisible() {
				return false;
			}

			@Override
			protected boolean getConfiguredDisplayable() {
				return false;
			}
		}
	}
}
