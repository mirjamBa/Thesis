package de.hsrm.thesis.filemanagement.client.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.forms.SearchBookmarkForm.MainBox.BookmarkBox;
import de.hsrm.thesis.filemanagement.client.forms.SearchBookmarkForm.MainBox.BookmarkBox.BookmarkField;
import de.hsrm.thesis.filemanagement.client.services.IFileSearchBookmarkService;
import de.hsrm.thesis.filemanagement.shared.forms.SearchBookmarkFormData;

@FormData(value = SearchBookmarkFormData.class, sdkCommand = SdkCommand.CREATE)
public class SearchBookmarkForm extends AbstractForm {

	private IForm m_form;

	public SearchBookmarkForm(IForm form) throws ProcessingException {
		super();
		m_form = form;
	}

	@Override
	protected void execInitForm() throws ProcessingException {
		loadTableData();
	}
	
	private void loadTableData() throws ProcessingException{
		BookmarkField.Table table = getBookmarkField().getTable();
		table.deleteAllRows();
		Object[][] result = SERVICES.getService(
				IFileSearchBookmarkService.class).getBookmarks();
		for (int i = 0; i < result.length; i++) {
			ITableRow row = table.createRow();
			String value = table.getBookmarkColumn().parseValue(row,
					result[i][0]);
			table.getBookmarkColumn().setValue(row, value);
			table.addRow(row);
		}
	}
	
	@Override
	protected int getConfiguredDisplayHint() {
		return DISPLAY_HINT_VIEW;
	}

	@Override
	protected String getConfiguredDisplayViewId() {
		return VIEW_ID_SW;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("SearchBookmarkForm");
	}

	public BookmarkBox getBookmarkBox() {
		return getFieldByClass(BookmarkBox.class);
	}

	public BookmarkField getBookmarkField() {
		return getFieldByClass(BookmarkField.class);
	}

	public void startNew() throws ProcessingException {
		startInternal(new NewHandler());
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Order(10.0)
		public class BookmarkBox extends AbstractGroupBox {
			
			@Order(10.0)
			public class BookmarkField
					extends
						AbstractTableField<BookmarkField.Table> {
				
				@Override
				protected void execReloadTableData() throws ProcessingException {
					loadTableData();
				}
				
				@Override
				protected int getConfiguredGridH() {
					return 15;
				}

				@Override
				protected int getConfiguredLabelPosition() {
					return LABEL_POSITION_TOP;
				}

				@Order(10.0)
				public class Table extends AbstractExtensibleTable {

					@Override
					protected boolean getConfiguredAutoResizeColumns() {
						return true;
					}

					public BookmarkColumn getBookmarkColumn() {
						return getColumnSet().getColumnByClass(
								BookmarkColumn.class);
					}

					@Order(10.0)
					public class BookmarkColumn extends AbstractStringColumn {

						@Override
						protected String getConfiguredHeaderText() {
							return TEXTS.get("Bookmark");
						}

					}

					@Order(10.0)
					public class LoadBookmarkMenu
							extends
								AbstractExtensibleMenu {

						@Override
						protected String getConfiguredText() {
							return TEXTS.get("LoadBookmark");
						}

						@Override
						protected void execAction() throws ProcessingException {
							String xml = SERVICES.getService(
									IFileSearchBookmarkService.class)
									.getBookmarkXml(
											getBookmarkColumn()
													.getSelectedValue());
							m_form.setXML(xml);
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
							SERVICES.getService(
									IFileSearchBookmarkService.class)
									.deleteBookmark(
											getBookmarkColumn()
													.getSelectedValue());
							getBookmarkField().getTable().deleteRow(getSelectedRow());
						}
					}
				}
			}

		}
	}

	public class NewHandler extends AbstractFormHandler {
	}
}
