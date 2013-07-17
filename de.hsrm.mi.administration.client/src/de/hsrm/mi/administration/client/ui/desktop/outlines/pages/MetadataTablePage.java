package de.hsrm.mi.administration.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.pages.AbstractExtensiblePageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.administration.client.ui.forms.AttributeForm;
import de.hsrm.thesis.filemanagement.shared.services.IAttributeService;
import de.hsrm.thesis.filemanagement.shared.services.code.DatatypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;

public class MetadataTablePage
		extends
			AbstractExtensiblePageWithTable<MetadataTablePage.Table> {

	private long m_filetypeNr;

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Metadata");
	}

	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}

	@Override
	protected Object[][] execLoadTableData(SearchFilter filter)
			throws ProcessingException {
		return SERVICES.getService(IAttributeService.class).getAttributes(
				m_filetypeNr);
	}

	@Order(10.0)
	public class Table extends AbstractExtensibleTable {

		@Override
		protected boolean getConfiguredMultiSelect() {
			return false;
		}

		public NameColumn getNameColumn() {
			return getColumnSet().getColumnByClass(NameColumn.class);
		}

		public DatatypeColumn getDatatypeColumn() {
			return getColumnSet().getColumnByClass(DatatypeColumn.class);
		}

		public FileTypeColumn getFileTypeColumn() {
			return getColumnSet().getColumnByClass(FileTypeColumn.class);
		}

		public ShowInFileTableColumn getShowInFileTableColumn() {
			return getColumnSet().getColumnByClass(ShowInFileTableColumn.class);
		}

		@Override
		protected void execRowAction(ITableRow row) throws ProcessingException {
			getMenu(ModifyMenu.class).doAction();
		}

		public AttributIDColumn getAttributIDColumn() {
			return getColumnSet().getColumnByClass(AttributIDColumn.class);
		}

		@Override
		protected boolean getConfiguredAutoResizeColumns() {
			return true;
		}

		@Order(10.0)
		public class AttributIDColumn extends AbstractLongColumn {

			@Override
			protected boolean getConfiguredDisplayable() {
				return false;
			}

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("AttributID");
			}

			@Override
			protected boolean getConfiguredVisible() {
				return false;
			}
		}

		@Order(20.0)
		public class NameColumn extends AbstractStringColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Name");
			}
		}

		@Order(30.0)
		public class ShowInFileTableColumn extends AbstractBooleanColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("ShowInFileTable");
			}
		}

		@Order(40.0)
		public class DatatypeColumn extends AbstractSmartColumn<Long> {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Datatype");
			}

			@Override
			protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
				return DatatypeCodeType.class;
			}

		}

		@Order(50.0)
		public class FileTypeColumn extends AbstractSmartColumn<Long> {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("FileType");
			}

			@Override
			protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
				return FileTypeCodeType.class;
			}
		}

		@Order(10.0)
		public class NewMetadataattributeMenu extends AbstractExtensibleMenu {

			@Override
			protected boolean getConfiguredSingleSelectionAction() {
				return false;
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("NewMetadataattribute");
			}

			@Override
			protected boolean getConfiguredEmptySpaceAction() {
				return true;
			}

			@Override
			protected void execAction() throws ProcessingException {
				AttributeForm form = new AttributeForm();
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
				AttributeForm form = new AttributeForm();
				form.setAttributeId(getAttributIDColumn().getSelectedValue());
				form.getDescriptionField().setValue(
						getNameColumn().getSelectedValue());
				form.getDatatypeField().setValue(
						getDatatypeColumn().getSelectedValue());
				form.getDatatypeField().setEnabled(false);
				form.getFileTypeField().setValue(
						getFileTypeColumn().getSelectedValue());
				form.getFileTypeField().setEnabled(false);
				form.getShowInFileTableField().setValue(
						getShowInFileTableColumn().getSelectedValue());
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
			protected void execInitAction() throws ProcessingException {
				setVisible(getFileTypeColumn().getSelectedValue() != null);
			}

			@Override
			protected void execAction() throws ProcessingException {
				SERVICES.getService(IAttributeService.class).delete(
						getAttributIDColumn().getSelectedValues());
				reloadPage();
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
