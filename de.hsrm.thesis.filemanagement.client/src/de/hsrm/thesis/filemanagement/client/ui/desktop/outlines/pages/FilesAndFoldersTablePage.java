package de.hsrm.thesis.filemanagement.client.ui.desktop.outlines.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.annotations.ConfigProperty;
import org.eclipse.scout.commons.annotations.ConfigPropertyValue;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.dnd.FileListTransferObject;
import org.eclipse.scout.commons.dnd.TransferObject;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractValueField;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.IDateField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.IStringField;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.pages.AbstractExtensiblePageWithTable;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.security.BasicHierarchyPermission;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.Activator;
import de.hsrm.thesis.filemanagement.client.forms.FoldersForm;
import de.hsrm.thesis.filemanagement.client.services.IClientFileService;
import de.hsrm.thesis.filemanagement.client.ui.ColumnFactory;
import de.hsrm.thesis.filemanagement.client.ui.DatatypeColumnFactory;
import de.hsrm.thesis.filemanagement.client.ui.forms.AuthorityForm;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm;
import de.hsrm.thesis.filemanagement.shared.formdata.FileSearchFormData;
import de.hsrm.thesis.filemanagement.shared.forms.CreateFoldersPermission;
import de.hsrm.thesis.filemanagement.shared.nonFormdataBeans.ColumnSpec;
import de.hsrm.thesis.filemanagement.shared.security.CreateFilePermission;
import de.hsrm.thesis.filemanagement.shared.security.DeleteFilePermission;
import de.hsrm.thesis.filemanagement.shared.security.DeleteFolderPermission;
import de.hsrm.thesis.filemanagement.shared.security.DownloadFilePermission;
import de.hsrm.thesis.filemanagement.shared.security.FreeFilePermission;
import de.hsrm.thesis.filemanagement.shared.security.FreeFolderPermission;
import de.hsrm.thesis.filemanagement.shared.security.OpenFilePermission;
import de.hsrm.thesis.filemanagement.shared.security.UpdateFilePermission;
import de.hsrm.thesis.filemanagement.shared.security.UpdateFolderPermission;
import de.hsrm.thesis.filemanagement.shared.services.IAttributeService;
import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.IFolderService;
import de.hsrm.thesis.filemanagement.shared.services.IMetadataService;
import de.hsrm.thesis.filemanagement.shared.services.IRoleProcessService;
import de.hsrm.thesis.filemanagement.shared.services.IStorageService;
import de.hsrm.thesis.filemanagement.shared.services.ITagService;
import de.hsrm.thesis.filemanagement.shared.services.lookup.UserLookupCall;
import de.hsrm.thesis.filemanagement.shared.utility.ArrayUtility;

public class FilesAndFoldersTablePage
		extends
			AbstractExtensiblePageWithTable<FilesAndFoldersTablePage.FilesAndFoldersTable> {
	private List<IColumn<?>> m_injectedColumns;
	private Long m_folderId;
	private String m_folderName;

	public FilesAndFoldersTablePage(Long folderId) {
		super();
		m_folderId = folderId;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("File");
	}

	@Override
	protected boolean getConfiguredExpanded() {
		return true;
	}

	@Override
	protected String getConfiguredIconId() {
		return AbstractIcons.TreeNode;
	}

	private Long[] extractIdsFromTableData(Object[][] data) {
		Long[] ids = new Long[data.length];
		for (int i = 0; i < data.length; i++) {
			ids[i] = (Long) data[i][0];
		}
		return ids;
	}

	@Override
	protected IPage execCreateChildPage(ITableRow row)
			throws ProcessingException {
		// only create a childPage for folders
		if (getTable().getIsFolderColumn().getValue(row)) {
			FolderTablePage page = new FolderTablePage(getTable()
					.getFileIdColumn().getValue(row));
			page.setFolderName(getTable().getNameColumn().getValue(row));
			return page;
		} else {
			// create a page with a form for files
			FileForm form = prepareFileForm(row);
			return new FilePage(form, getTable().getFileIdColumn()
					.getValue(row));
		}
	}

	@Override
	protected Object[][] execLoadTableData(SearchFilter filter)
			throws ProcessingException {
		// standard table data
		Object[][] fileTableData = SERVICES.getService(IFileService.class)
				.getFolderFiles((FileSearchFormData) filter.getFormData(),
						getFolderId());

		// dictionary dynamic columns (attribute name and datatype)
		Map<Object, Object> map = SERVICES.getService(IAttributeService.class)
				.getDisplayedAttributeNamesAndDatatype();

		String[] columnNames = Arrays.copyOf(map.keySet().toArray(), map
				.keySet().toArray().length, String[].class);

		ColumnSpec[] columnSpecs = new ColumnSpec[columnNames.length];
		for (int i = 0; i < columnSpecs.length; i++) {
			Long datatype = (Long) map.get(columnNames[i]);
			columnSpecs[i] = new ColumnSpec(
					((Integer) columnNames[i].hashCode()).toString(),
					columnNames[i], datatype);
		}

		// dynamic column data
		Object[][] dynamicColumnData = SERVICES.getService(
				IMetadataService.class).getMetadataForFiles(
				extractIdsFromTableData(fileTableData), columnNames);

		// concat standard and dynamic column data
		Object[][] allTableData = ArrayUtility.concatArrays(fileTableData,
				dynamicColumnData);

		updateDynamicColumns(columnSpecs);

		return allTableData;

	}

	private void updateDynamicColumns(ColumnSpec[] columnSpecs) {
		if (columnSpecs.length == 0) {
			return;
		}
		FilesAndFoldersTable table = getTable();
		ColumnFactory columnFactory = new DatatypeColumnFactory();
		m_injectedColumns = new ArrayList<IColumn<?>>();
		for (ColumnSpec spec : columnSpecs) {
			m_injectedColumns.add(columnFactory.createColumn(spec.getType(),
					spec.getId(), spec.getText()));
		}
		table.resetColumnConfiguration();
	}

	@Order(10.0)
	public class FilesAndFoldersTable extends AbstractExtensibleTable {

		@Override
		protected boolean getConfiguredAutoResizeColumns() {
			return true;
		}

		@Override
		@ConfigProperty("DRAG_AND_DROP_TYPE")
		@ConfigPropertyValue("0")
		protected int getConfiguredDropType() {
			return TYPE_FILE_TRANSFER | TYPE_TEXT_TRANSFER
					| TYPE_JAVA_ELEMENT_TRANSFER;
		}

		@Override
		protected void execDrop(ITableRow row, TransferObject t)
				throws ProcessingException {
			if (t.isFileList()) {
				File[] files = ((FileListTransferObject) t).getFiles();
				for (int i = 0; i < files.length; ++i) {
					Activator.getDefault().handle(files[i], getFolderId());
				}
				reloadPage();
			}
		}

		protected void execRowAction(ITableRow row) throws ProcessingException {
			getMenu(ModifyMenu.class).doAction();
		}

		@Override
		protected void injectColumnsInternal(List<IColumn<?>> columnList) {
			if (m_injectedColumns != null) {
				columnList.addAll(m_injectedColumns);
			}
		}

		public TypistColumn getTypistColumn() {
			return getColumnSet().getColumnByClass(TypistColumn.class);
		}

		public NameColumn getNameColumn() {
			return getColumnSet().getColumnByClass(NameColumn.class);
		}

		public IsFolderColumn getIsFolderColumn() {
			return getColumnSet().getColumnByClass(IsFolderColumn.class);
		}

		// public IconColumn getIconColumn() {
		// return getColumnSet().getColumnByClass(IconColumn.class);
		// }

		public FileIdColumn getFileIdColumn() {
			return getColumnSet().getColumnByClass(FileIdColumn.class);
		}

		@Order(10.0)
		public class FileIdColumn extends AbstractLongColumn {

			@Override
			protected boolean getConfiguredDisplayable() {
				return false;
			}

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("FileId");
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

			@Override
			protected void execDecorateCell(Cell cell, ITableRow row)
					throws ProcessingException {
				if (getIsFolderColumn().getValue(row)) {
					row.setIconId(AbstractIcons.TreeNode);
				} else {
					row.setIconId(AbstractIcons.Bookmark);
				}
			}

			@Override
			protected boolean getConfiguredSummary() {
				return true;
			}
		}

		@Order(30.0)
		public class TypistColumn extends AbstractSmartColumn<Long> {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Typist");
			}

			@Override
			protected Class<? extends LookupCall> getConfiguredLookupCall() {
				return UserLookupCall.class;
			}

			@Override
			protected int getConfiguredWidth() {
				return 200;
			}
		}

		@Order(40.0)
		public class IsFolderColumn extends AbstractBooleanColumn {

			@Override
			protected boolean getConfiguredDisplayable() {
				return false;
			}

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("IsFolder");
			}

			@Override
			protected boolean getConfiguredVisible() {
				return false;
			}
		}

		// @Order(50.0)
		// public class IconColumn extends AbstractStringColumn {
		//
		// @Override
		// protected boolean getConfiguredDisplayable() {
		// return false;
		// }
		//
		// @Override
		// protected boolean getConfiguredVisible() {
		// return false;
		// }
		//
		// @Override
		// protected void execDecorateCell(Cell cell, ITableRow row)
		// throws ProcessingException {
		// if (getIsFolderColumn().getValue(row)) {
		// row.setIconId(AbstractIcons.TreeNode);
		// } else {
		// row.setIconId(AbstractIcons.Bookmark);
		// }
		// }
		// }

		@Order(10.0)
		public class NewFileMenu extends AbstractExtensibleMenu {

			@Override
			protected void execPrepareAction() throws ProcessingException {
				if (getTree().getSelectedNode() instanceof FilePage) {
					setVisible(false);
				} else {
					setVisible(true);
				}
				if (isVisible()) {
					setVisibleGranted(ACCESS
							.getLevel(new CreateFilePermission()) > BasicHierarchyPermission.LEVEL_NONE);
				}

			}

			@Override
			protected boolean getConfiguredEmptySpaceAction() {
				return true;
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("NewFile");
			}

			@Override
			protected void execAction() throws ProcessingException {
				Activator.getDefault()
						.handle(null,
								((FilesAndFoldersTablePage) getTree()
										.getSelectedNode()).getFolderId());
				reloadPage();
			}
		}

		@Order(20.0)
		public class NewFolderMenu extends AbstractExtensibleMenu {

			@Override
			protected void execPrepareAction() throws ProcessingException {
				if (getTree().getSelectedNode() instanceof FilePage) {
					setVisible(false);
				} else {
					setVisible(true);
				}
				if (isVisible()) {
					setVisibleGranted(ACCESS
							.getLevel(new CreateFoldersPermission()) > BasicHierarchyPermission.LEVEL_NONE);
				}
			}

			@Override
			protected boolean getConfiguredEmptySpaceAction() {
				return true;
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("NewFolder");
			}

			@Override
			protected void execAction() throws ProcessingException {
				FoldersForm form = new FoldersForm();
				form.setParentFolderId(((FilesAndFoldersTablePage) getTree()
						.getSelectedNode()).getFolderId());
				form.startNew();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(30.0)
		public class ModifyFolderMenu extends AbstractExtensibleMenu {

			@Override
			protected void execPrepareAction() throws ProcessingException {
				if (getTree().getSelectedNode() instanceof FilePage) {
					setVisible(false);
				}
				if (isVisible()) {
					setVisibleGranted(ACCESS
							.getLevel(new UpdateFolderPermission()) > BasicHierarchyPermission.LEVEL_NONE);
				}
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("ModifyFolder");
			}

			@Override
			protected void execAction() throws ProcessingException {
				FoldersForm form = new FoldersForm();
				form.setFolderId(((FilesAndFoldersTablePage) getTree()
						.getSelectedNode()).getFolderId());
				form.getNameField()
						.setValue(
								((FilesAndFoldersTablePage) getTree()
										.getSelectedNode()).getFolderName());
				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(40.0)
		public class DeleteFolderMenu extends AbstractExtensibleMenu {

			@Override
			protected void execPrepareAction() throws ProcessingException {
				if (getTree().getSelectedNode() instanceof FilePage) {
					setVisible(false);
				}
				if (isVisible()) {
					setVisibleGranted(ACCESS
							.getLevel(new DeleteFolderPermission()) > BasicHierarchyPermission.LEVEL_NONE);
				}
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("DeleteFolderMenu");
			}

			@Override
			protected void execAction() throws ProcessingException {
				SERVICES.getService(IFolderService.class)
						.deleteFolderAndChildFolders(
								((FilesAndFoldersTablePage) getTree()
										.getSelectedNode()).getFolderId());
				reloadPage();
			}
		}

		@Order(50.0)
		public class AuthorityFolderMenu extends AbstractExtensibleMenu {

			@Override
			protected void execPrepareAction() throws ProcessingException {
				if (getTree().getSelectedNode() instanceof FilePage) {
					setVisible(false);
				}
				if (isVisible()) {
					setVisibleGranted(ACCESS
							.getLevel(new FreeFolderPermission()) > BasicHierarchyPermission.LEVEL_NONE);
				}
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("AuthorityFolder");
			}

			@Override
			protected void execAction() throws ProcessingException {
				Long[] roleIds = SERVICES.getService(IRoleProcessService.class)
						.getApprovedRolesForFileOrFolder(
								((FilesAndFoldersTablePage) getTree()
										.getSelectedNode()).getFolderId());
				AuthorityForm form = new AuthorityForm(
						((FilesAndFoldersTablePage) getTree().getSelectedNode())
								.getFolderId(), roleIds);
				form.startNew();
				form.waitFor();
			}
		}

		@Order(60.0)
		public class OpenFileMenu extends AbstractExtensibleMenu {

			@Override
			protected void execPrepareAction() throws ProcessingException {
				setVisibleGranted(ACCESS.getLevel(new OpenFilePermission()) > BasicHierarchyPermission.LEVEL_NONE);
				setVisible(!getIsFolderColumn().getSelectedValue());
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("OpenFile");
			}

			@Override
			protected void execAction() throws ProcessingException {
				SERVICES.getService(IClientFileService.class).openFile(
						getFileIdColumn().getSelectedValue());
			}
		}

		@Order(70.0)
		public class DeleteMenu extends AbstractExtensibleMenu {

			@Override
			protected void execPrepareAction() throws ProcessingException {
				setVisibleGranted(ACCESS.getLevel(new DeleteFilePermission()) > BasicHierarchyPermission.LEVEL_NONE);
				setVisible(!getIsFolderColumn().getSelectedValue());
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("DeleteMenu");
			}

			@Override
			protected void execAction() throws ProcessingException {
				SERVICES.getService(IFileService.class).deleteFile(
						getFileIdColumn().getSelectedValue());
				reloadPage();
			}
		}

		@Order(80.0)
		public class AuthorityMenu extends AbstractExtensibleMenu {

			@Override
			protected void execPrepareAction() throws ProcessingException {
				setVisibleGranted(ACCESS.getLevel(new FreeFilePermission()) > BasicHierarchyPermission.LEVEL_NONE);
				setVisible(!getIsFolderColumn().getSelectedValue());
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Unfreeze");
			}

			@Override
			protected void execAction() throws ProcessingException {
				FileForm form = new FileForm(getFileIdColumn()
						.getSelectedValue());

				form.getMetadataBox().setVisible(false);
				form.getDetailedBox().setVisible(false);
				form.getTagBox().setVisible(false);

				// FIXME Role Form auslagern
				form.getTitleField().setMandatory(false);

				form.getAuthorityBox()
						.getRolesField()
						.setValue(
								SERVICES.getService(IRoleProcessService.class)
										.getApprovedRolesForFileOrFolder(
												getFileIdColumn()
														.getSelectedValue()));

				form.startUpdateAuthorities();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(90.0)
		public class ModifyMenu extends AbstractExtensibleMenu {

			@Override
			protected void execPrepareAction() throws ProcessingException {
				setVisibleGranted(ACCESS.getLevel(new UpdateFilePermission()) > BasicHierarchyPermission.LEVEL_NONE);
				setVisible(!getIsFolderColumn().getSelectedValue());
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Modify");
			}

			@Override
			protected void execAction() throws ProcessingException {
				FileForm form = prepareFileForm(getSelectedRow());
				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(100.0)
		public class DownloadFileMenu extends AbstractExtensibleMenu {

			@Override
			protected void execPrepareAction() throws ProcessingException {
				setVisibleGranted(ACCESS.getLevel(new DownloadFilePermission()) > BasicHierarchyPermission.LEVEL_NONE);
				setVisible(!getIsFolderColumn().getSelectedValue());
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("DownloadFile");
			}

			@Override
			protected void execAction() throws ProcessingException {
				File f = SERVICES.getService(IFileService.class).getServerFile(
						getTable().getFileIdColumn().getSelectedValue());
				String filename = IOUtility.getFileName(f.getAbsolutePath());

				File tempFile;
				if (UserAgentUtility.isWebClient()) {
					tempFile = new File(
							IOUtility.createTempDirectory("download"), filename);
				} else {
					FileChooser chooser = new FileChooser();
					chooser.setTypeLoad(false);
					chooser.setFileName(filename);
					File[] files = chooser.startChooser();
					tempFile = (files != null && files.length > 0
							? files[0]
							: null);
				}

				SERVICES.getService(IClientFileService.class).downloadFile(f,
						tempFile);

			}
		}

	}

	@SuppressWarnings("unchecked")
	protected FileForm prepareFileForm(ITableRow row)
			throws ProcessingException {
		IMetadataService service = SERVICES.getService(IMetadataService.class);

		Long fileId = getTable().getFileIdColumn().getValue(row);

		FileForm form = new FileForm(fileId);

		Long filetype = (Long) SERVICES.getService(IMetadataService.class)
				.getMetadataValue(TEXTS.get("FileType"), fileId);

		Map<String, Object> metadata = service.getMetdataMapForFile(fileId,
				filetype);

		// metadata
		form.getTypistField().setValue(
				getTable().getTypistColumn().getValue(row));
		form.getFilesizeField().setValue(
				(String) metadata.get(IStorageService.META_FILESIZE));
		form.getCreationDateField().setValue(
				(Date) metadata.get(IStorageService.META_ENTRYDATE));
		form.getFileExtensionField().setValue(
				(String) metadata.get(IStorageService.META_FILEEXTENSION));

		// DCMI metadata
		IFormField[] fields = form.getDCMIBox().getFields();
		for (IFormField field : fields) {
			String label = field.getLabel();
			Object value = metadata.get(label);

			if (field instanceof AbstractValueField<?>) {
				field = (AbstractValueField<?>) field;
				if (field instanceof IStringField) {
					((AbstractValueField<String>) field)
							.setValue((String) value);
				} else if (field instanceof IDateField) {
					((AbstractValueField<Date>) field).setValue((Date) value);
				}

			}
		}
		form.setFiletypeNr(filetype);
		form.getFileTypeField().setValue(filetype);

		// tags
		form.getAvailableTagsBox().setValue(
				SERVICES.getService(ITagService.class).getTagsForFile(fileId));

		// authority
		form.getAuthorityBox()
				.getRolesField()
				.setValue(
						SERVICES.getService(IRoleProcessService.class)
								.getApprovedRolesForFileOrFolder(fileId));

		return form;
	}

	@Override
	protected Class<? extends ISearchForm> getConfiguredSearchForm() {
		return FileSearchForm.class;
	}

	@FormData
	public Long getFolderId() {
		return m_folderId;
	}

	@FormData
	public void setFolderId(Long folderId) {
		m_folderId = folderId;
	}

	@FormData
	public String getFolderName() {
		return m_folderName;
	}

	@FormData
	public void setFolderName(String folderName) {
		m_folderName = folderName;
	}
}
