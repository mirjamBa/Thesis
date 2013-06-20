package de.hsrm.mi.administration.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.administration.client.ui.forms.MetadataForm;
import de.hsrm.thesis.filemanagement.shared.services.IMetadataService;
import de.hsrm.thesis.filemanagement.shared.services.code.DatatypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;

public class MetadataTablePage extends
		AbstractPageWithTable<MetadataTablePage.Table> {

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
		return SERVICES.getService(IMetadataService.class).getAttributes(
				m_filetypeNr);
	}

	@Order(10.0)
	public class Table extends AbstractExtensibleTable {

		public NameColumn getNameColumn() {
			return getColumnSet().getColumnByClass(NameColumn.class);
		}

		public DatatypeColumn getDatatypeColumn() {
			return getColumnSet().getColumnByClass(DatatypeColumn.class);
		}

		public FileTypeColumn getFileTypeColumn() {
			return getColumnSet().getColumnByClass(FileTypeColumn.class);
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

		@Order(40.0)
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
			protected String getConfiguredText() {
				return TEXTS.get("NewMetadataattribute");
			}

			@Override
			protected boolean getConfiguredEmptySpaceAction() {
				return true;
			}

			@Override
			protected void execAction() throws ProcessingException {
				MetadataForm form = new MetadataForm();
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
				MetadataForm form = new MetadataForm();
				form.setAttributeId(getAttributIDColumn().getSelectedValue());
				form.getDescriptionField().setValue(getNameColumn().getSelectedValue());
				form.getDatatypeField().setValue(getDatatypeColumn().getSelectedValue());
				form.getDatatypeField().setEnabled(false);
				form.getFileTypeField().setValue(getFileTypeColumn().getSelectedValue());
				form.getFileTypeField().setEnabled(false);
				form.startModify();
				form.waitFor();
				if(form.isFormStored()){
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
			protected void execAction() throws ProcessingException {
				SERVICES.getService(IMetadataService.class).delete(getAttributIDColumn().getSelectedValues());
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
