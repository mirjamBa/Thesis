package de.hsrm.mi.administration.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.pages.AbstractExtensiblePageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.security.BasicHierarchyPermission;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.administration.client.ui.forms.VersionForm;
import de.hsrm.mi.administration.shared.security.ViewFiletypeTablePagePermission;
import de.hsrm.thesis.filemanagement.shared.services.IFiletypeService;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;

public class FileTypeTablePage
		extends
			AbstractExtensiblePageWithTable<FileTypeTablePage.Table> {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("FileType");
	}

	@Override
	protected IPage execCreateChildPage(ITableRow row)
			throws ProcessingException {
		FiletypeAttributesNodePage childPage = new FiletypeAttributesNodePage();
		childPage.setFiletypeNr(getTable().getFiletypeColumn().getValue(row));
		return childPage;
	}

	@Override
	protected void execInitPage() throws ProcessingException {
		setVisibleGranted(ACCESS.getLevel(new ViewFiletypeTablePagePermission()) > BasicHierarchyPermission.LEVEL_NONE);
	}

	@Override
	protected Object[][] execLoadTableData(SearchFilter filter)
			throws ProcessingException {
		return SERVICES.getService(IFiletypeService.class).getFiletypes();
	}

	@Order(10.0)
	public class Table extends AbstractExtensibleTable {

		public FiletypeColumn getFiletypeColumn() {
			return getColumnSet().getColumnByClass(FiletypeColumn.class);
		}

		public VersionColumn getVersionColumn() {
			return getColumnSet().getColumnByClass(VersionColumn.class);
		}

		@Order(20.0)
		public class FiletypeColumn extends AbstractSmartColumn<Long> {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Name");
			}

			@Override
			protected int getConfiguredWidth() {
				return 200;
			}

			@Override
			protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
				return FileTypeCodeType.class;
			}

		}

		@Order(30.0)
		public class VersionColumn extends AbstractBooleanColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Version");
			}

			@Override
			protected int getConfiguredWidth() {
				return 200;
			}
		}

		@Order(10.0)
		public class ModifyMenu extends AbstractExtensibleMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Modify");
			}

			@Override
			protected void execAction() throws ProcessingException {
				VersionForm form = new VersionForm();
				form.getFileTypeField().setValue(
						getFiletypeColumn().getSelectedValue());
				form.getVersionField().setValue(
						getVersionColumn().getSelectedValue());
				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}

			}
		}
	}
}
