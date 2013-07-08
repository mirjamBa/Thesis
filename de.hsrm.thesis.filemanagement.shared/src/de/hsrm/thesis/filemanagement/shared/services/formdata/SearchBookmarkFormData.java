package de.hsrm.thesis.filemanagement.shared.services.formdata;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldData;
public class SearchBookmarkFormData extends AbstractFormData {
	private static final long serialVersionUID = 1L;

	public SearchBookmarkFormData() {
	}

	public Bookmark getBookmark() {
		return getFieldByClass(Bookmark.class);
	}
	public static class Bookmark extends AbstractTableFieldData {
		private static final long serialVersionUID = 1L;

		public Bookmark() {
		}

		public static final int BOOKMARK_COLUMN_ID = 0;
		public void setBookmark(int row, String bookmark) {
			setValueInternal(row, BOOKMARK_COLUMN_ID, bookmark);
		}
		public String getBookmark(int row) {
			return (String) getValueInternal(row, BOOKMARK_COLUMN_ID);
		}
		@Override
		public int getColumnCount() {
			return 1;
		}
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
				case BOOKMARK_COLUMN_ID :
					return getBookmark(row);
				default :
					return null;
			}
		}
		@Override
		public void setValueAt(int row, int column, Object value) {
			switch (column) {
				case BOOKMARK_COLUMN_ID :
					setBookmark(row, (String) value);
					break;
			}
		}
	}
}
