package de.hsrm.perfunctio.core.shared.services.formdata;

import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldData;

/**
 * Data transfer object for the MetdataTableField
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class MetadataTableFieldData extends AbstractTableFieldData{
	private static final long serialVersionUID = 1L;

	public MetadataTableFieldData() {
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
