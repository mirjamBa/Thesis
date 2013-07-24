package de.hsrm.perfunctio.core.shared.services.formdata;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldData;
public class PagingFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public PagingFormData() {
	}

	public Hierarchy getHierarchy() {
		return getFieldByClass(Hierarchy.class);
	}
	public Information getInformation() {
		return getFieldByClass(Information.class);
	}
	public Page getPage() {
		return getFieldByClass(Page.class);
	}
	public static class Hierarchy extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Hierarchy() {
		}
	}
	public static class Information extends AbstractTableFieldData {
		private static final long serialVersionUID = 1L;

		public Information() {
		}

		public static final int INFORMATION_COLUMN_ID = 0;
		public static final int VALUE_COLUMN_ID = 1;
		public void setInformation(int row, String information) {
			setValueInternal(row, INFORMATION_COLUMN_ID, information);
		}
		public String getInformation(int row) {
			return (String) getValueInternal(row, INFORMATION_COLUMN_ID);
		}
		public void setValue(int row, String value) {
			setValueInternal(row, VALUE_COLUMN_ID, value);
		}
		public String getValue(int row) {
			return (String) getValueInternal(row, VALUE_COLUMN_ID);
		}
		@Override
		public int getColumnCount() {
			return 2;
		}
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
				case INFORMATION_COLUMN_ID :
					return getInformation(row);
				case VALUE_COLUMN_ID :
					return getValue(row);
				default :
					return null;
			}
		}
		@Override
		public void setValueAt(int row, int column, Object value) {
			switch (column) {
				case INFORMATION_COLUMN_ID :
					setInformation(row, (String) value);
					break;
				case VALUE_COLUMN_ID :
					setValue(row, (String) value);
					break;
			}
		}
	}
	public static class Page extends AbstractValueFieldData<String> {
		private static final long serialVersionUID = 1L;

		public Page() {
		}
	}
}
