package de.hsrm.perfunctio.core.shared.services.formdata;

import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldData;

/**
 * Data transfer object for the PermissionTableField
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class PermissionTableFieldData extends AbstractTableFieldData {
	private static final long serialVersionUID = 1L;

	public PermissionTableFieldData() {
	}

	/**
	 * list of derived validation rules.
	 */
	@Override
	protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
		super.initValidationRules(ruleMap);
	}
	public static final int PERMISSION_COLUMN_ID = 0;
	public static final int OK_COLUMN_ID = 1;
	public void setPermission(int row, String permission) {
		setValueInternal(row, PERMISSION_COLUMN_ID, permission);
	}
	public String getPermission(int row) {
		return (String) getValueInternal(row, PERMISSION_COLUMN_ID);
	}
	public void setOk(int row, Boolean ok) {
		setValueInternal(row, OK_COLUMN_ID, ok);
	}
	public Boolean getOk(int row) {
		return (Boolean) getValueInternal(row, OK_COLUMN_ID);
	}
	@Override
	public int getColumnCount() {
		return 2;
	}
	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
			case PERMISSION_COLUMN_ID :
				return getPermission(row);
			case OK_COLUMN_ID :
				return getOk(row);
			default :
				return null;
		}
	}
	@Override
	public void setValueAt(int row, int column, Object value) {
		switch (column) {
			case PERMISSION_COLUMN_ID :
				setPermission(row, (String) value);
				break;
			case OK_COLUMN_ID :
				setOk(row, (Boolean) value);
				break;
		}
	}
}
