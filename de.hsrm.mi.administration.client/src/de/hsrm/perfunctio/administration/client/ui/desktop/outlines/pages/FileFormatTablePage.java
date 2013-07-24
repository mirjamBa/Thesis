package de.hsrm.perfunctio.administration.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.client.ui.forms.FileFormatForm;
import de.hsrm.perfunctio.core.shared.services.IFileFormatService;
import de.hsrm.perfunctio.core.shared.services.code.FileTypeCodeType;

public class FileFormatTablePage
		extends
			AbstractPageWithTable<FileFormatTablePage.Table> {

	private long m_filetypeNr;

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("FileFormat");
	}

	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}

	@Override
	protected Object[][] execLoadTableData(SearchFilter filter)
			throws ProcessingException {
		return SERVICES.getService(IFileFormatService.class).getFileFormats(
				getFiletypeNr());
	}

	@Order(10.0)
	public class Table extends AbstractExtensibleTable {

		public FileTypeColumn getFileTypeColumn() {
			return getColumnSet().getColumnByClass(FileTypeColumn.class);
		}

		public IdColumn getIDColumn() {
			return getColumnSet().getColumnByClass(IdColumn.class);
		}

		@Override
		protected void execRowAction(ITableRow row) throws ProcessingException {
			getMenu(ModifyMenu.class).doAction();
		}

		public FileFormatColumn getFileFormatColumn() {
			return getColumnSet().getColumnByClass(FileFormatColumn.class);
		}

		@Override
		protected boolean getConfiguredAutoResizeColumns() {
			return true;
		}

		@Override
		protected boolean getConfiguredMultiSelect() {
			return false;
		}

		@Order(10.0)
		public class IdColumn extends AbstractLongColumn {

			@Override
			protected boolean getConfiguredDisplayable() {
				return false;
			}

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("ID");
			}

			@Override
			protected boolean getConfiguredAutoOptimizeWidth() {
				return true;
			}

			@Override
			protected boolean getConfiguredVisible() {
				return false;
			}
		}

		@Order(20.0)
		public class FileFormatColumn extends AbstractStringColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("FileFormat");
			}

			@Override
			protected boolean getConfiguredAutoOptimizeWidth() {
				return true;
			}
		}

		@Order(30.0)
		public class FileTypeColumn extends AbstractSmartColumn<Long> {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("FileType");
			}

			@Override
			protected boolean getConfiguredAutoOptimizeWidth() {
				return true;
			}

			@Override
			protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
				return FileTypeCodeType.class;
			}

		}

		@Order(10.0)
		public class NewFormatMenu extends AbstractMenu {

			@Override
			protected boolean getConfiguredEmptySpaceAction() {
				return true;
			}

			@Override
			protected boolean getConfiguredSingleSelectionAction() {
				return false;
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("NewFormat");
			}

			@Override
			protected void execAction() throws ProcessingException {
				FileFormatForm form = new FileFormatForm();
				if (getFiletypeNr() > 0) {
					form.getFileTypeField().setValue(getFiletypeNr());
				}
				form.startNew();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}

			}
		}

		@Order(20.0)
		public class ModifyMenu extends AbstractExtensibleMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Modify");
			}

			@Override
			protected void execAction() throws ProcessingException {
				FileFormatForm form = new FileFormatForm();
				form.setId(getIDColumn().getSelectedValue());
				form.getFileFormatField().setValue(
						getFileFormatColumn().getSelectedValue());
				form.getFileTypeField().setValue(
						getFileTypeColumn().getSelectedValue());
				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(30.0)
		public class DeleteMenu extends AbstractExtensibleMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("DeleteMenu");
			}

			@Override
			protected boolean getConfiguredMultiSelectionAction() {
				return true;
			}

			@Override
			protected void execAction() throws ProcessingException {
				if (MessageBox.showDeleteConfirmationMessage(TEXTS
						.get("FileFormat"),
						getFileFormatColumn().getValues(getSelectedRows()))) {
					SERVICES.getService(IFileFormatService.class).delete(
							getIDColumn().getValues(getSelectedRows()));
					reloadPage();
				}
			}
		}
	}

	@FormData
	public long getFiletypeNr() {
		return m_filetypeNr;
	}

	@FormData
	public void setFiletypeNr(long filetypeNr) {
		m_filetypeNr = filetypeNr;
	}
}
