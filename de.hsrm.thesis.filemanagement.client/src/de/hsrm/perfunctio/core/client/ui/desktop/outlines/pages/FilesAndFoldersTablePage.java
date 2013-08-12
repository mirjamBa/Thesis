package de.hsrm.perfunctio.core.client.ui.desktop.outlines.pages;

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
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
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
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.client.Activator;
import de.hsrm.perfunctio.core.client.handler.FileUploadData;
import de.hsrm.perfunctio.core.client.services.IClientFileService;
import de.hsrm.perfunctio.core.client.ui.ColumnFactory;
import de.hsrm.perfunctio.core.client.ui.DatatypeColumnFactory;
import de.hsrm.perfunctio.core.client.ui.IPermissionGrantedMenu;
import de.hsrm.perfunctio.core.client.ui.forms.AuthorityForm;
import de.hsrm.perfunctio.core.client.ui.forms.FileForm;
import de.hsrm.perfunctio.core.client.ui.forms.FileSearchForm;
import de.hsrm.perfunctio.core.client.ui.forms.FoldersForm;
import de.hsrm.perfunctio.core.client.ui.forms.ImagePreviewForm;
import de.hsrm.perfunctio.core.shared.Icons;
import de.hsrm.perfunctio.core.shared.beans.ColumnSpec;
import de.hsrm.perfunctio.core.shared.security.CreateFilePermission;
import de.hsrm.perfunctio.core.shared.security.CreateFolderPermission;
import de.hsrm.perfunctio.core.shared.security.DeleteFilePermission;
import de.hsrm.perfunctio.core.shared.security.DeleteFolderPermission;
import de.hsrm.perfunctio.core.shared.security.DownloadFilePermission;
import de.hsrm.perfunctio.core.shared.security.FreeFilePermission;
import de.hsrm.perfunctio.core.shared.security.FreeFolderPermission;
import de.hsrm.perfunctio.core.shared.security.OpenFilePermission;
import de.hsrm.perfunctio.core.shared.security.UpdateFilePermission;
import de.hsrm.perfunctio.core.shared.security.UpdateFolderPermission;
import de.hsrm.perfunctio.core.shared.services.IAttributeService;
import de.hsrm.perfunctio.core.shared.services.IFileAndFolderFreeingService;
import de.hsrm.perfunctio.core.shared.services.IFileService;
import de.hsrm.perfunctio.core.shared.services.IFolderService;
import de.hsrm.perfunctio.core.shared.services.IMetadataService;
import de.hsrm.perfunctio.core.shared.services.IPermissionControlService;
import de.hsrm.perfunctio.core.shared.services.IStorageService;
import de.hsrm.perfunctio.core.shared.services.ITagService;
import de.hsrm.perfunctio.core.shared.services.IUserProcessService;
import de.hsrm.perfunctio.core.shared.services.code.DublinCoreMetadataElementSetCodeType;
import de.hsrm.perfunctio.core.shared.services.code.FileTypeCodeType;
import de.hsrm.perfunctio.core.shared.services.formdata.FileSearchFormData;
import de.hsrm.perfunctio.core.shared.services.lookup.UserLookupCall;
import de.hsrm.perfunctio.core.shared.utility.ArrayUtility;

/**
 * File display class, contains a file table for all attributes and a search
 * form. Displays files and folders (FilePage as child page for every file and
 * FoldersTablePage as child page for every folder)
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class FilesAndFoldersTablePage
		extends
			AbstractExtensiblePageWithTable<FilesAndFoldersTablePage.FilesAndFoldersTable> {
	protected List<IColumn<?>> m_injectedColumns;
	private Long m_folderId;
	private String m_folderName;

	public FilesAndFoldersTablePage() {
	}

	public FilesAndFoldersTablePage(Long folderId) {
		super();
		m_folderId = folderId;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("FileStorage");
	}

	@Override
	protected boolean getConfiguredExpanded() {
		return true;
	}

	@Override
	protected String getConfiguredIconId() {
		return Icons.Database;
	}

	public IMenu[] getMenuArray() {
		return getTable().getMenus();
	}

	protected Long[] extractIdsFromTableData(Object[][] data)
			throws ProcessingException {
		Long[] ids = new Long[data.length];
		for (int i = 0; i < data.length; i++) {
			ids[i] = (Long) data[i][0];
		}
		return ids;
	}

	@Override
	protected IPage execCreateChildPage(ITableRow row)
			throws ProcessingException {
		// only create a childTablePage for folders
		if (getTable().getIsFolderColumn().getValue(row)) {
			FolderTablePage page = new FolderTablePage(getTable()
					.getFileFolderIdColumn().getValue(row));
			page.setFolderName(getTable().getNameColumn().getValue(row));
			return page;
		} else {
			// create a page with a form for files
			FileForm form = prepareFileForm(row);
			Long fileId = getTable().getFileFolderIdColumn().getValue(row);
			preview(fileId);
			return new FilePage(form, fileId);
		}
	}

	protected void preview(Long fileId) throws ProcessingException {
		// show 5 seconds preview for images
		ICode<Long> code = CODES
				.getCode(DublinCoreMetadataElementSetCodeType.TypeCode.class);
		if (SERVICES.getService(IMetadataService.class)
				.getMetadataValue(code.getText(), fileId)
				.equals(FileTypeCodeType.ImageCode.ID)) {
			((FileSearchForm) getSearchFormInternal()).closeForm();
			ImagePreviewForm img = new ImagePreviewForm();
			File f = SERVICES.getService(IFileService.class).getServerFile(
					fileId);
			byte[] content = IOUtility.getContent(f.getAbsolutePath());
			img.getImagePreviewField().setImage(content);
			img.startNew();
		}
	}

	protected FileSearchFormData getSearchData(SearchFilter filter) {
		return (FileSearchFormData) filter.getFormData();
	}

	@Override
	protected Object[][] execLoadTableData(SearchFilter filter)
			throws ProcessingException {
		// standard table data
		Object[][] fileTableData = SERVICES.getService(IFileService.class)
				.getFolderFiles(getSearchData(filter), getFolderId());

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

	protected void updateDynamicColumns(ColumnSpec[] columnSpecs) {
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
					FileUploadData data = new FileUploadData();
					data.setFile(files[i]);
					data.setParentFolderId(getFolderId());
					Activator.getDefault().handle(data);
				}
				reloadPage();
			}
		}

		@Override
		protected void execRowAction(ITableRow row) throws ProcessingException {
			if (!getIsFolderColumn().getValue(row)) {
				getMenu(ModifyMenu.class).doAction();
			}
		}
		
		@Override
		protected void execRowClick(ITableRow row) throws ProcessingException {
			if (!getIsFolderColumn().getValue(row)) {
				preview(getFileFolderIdColumn().getValue(row));
			}
		}

		@Override
		protected boolean getConfiguredMultiSelect() {
			return false;
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

		public FileFolderIdColumn getFileFolderIdColumn() {
			return getColumnSet().getColumnByClass(FileFolderIdColumn.class);
		}

		@Order(10.0)
		public class FileFolderIdColumn extends AbstractLongColumn {

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
					row.setIconId(Icons.File);
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

		protected Long getFolderId() {
			if (getTable().getIsFolderColumn().getSelectedValue() != null
					&& getTable().getIsFolderColumn().getSelectedValue() == true) {
				return getTable().getFileFolderIdColumn().getSelectedValue();
			} else {
				return ((FilesAndFoldersTablePage) getTree().getSelectedNode())
						.getFolderId();
			}
		}

		protected String getFolderName() {
			if (getTable().getIsFolderColumn().getSelectedValue() != null
					&& getTable().getIsFolderColumn().getSelectedValue() == true) {
				return getTable().getNameColumn().getSelectedValue();
			} else {
				return ((FilesAndFoldersTablePage) getTree().getSelectedNode())
						.getFolderName();
			}
		}

		protected boolean selectedResourceIsIFile() {
			if (getTree().getSelectedNode() instanceof FilePage
					|| (getTable().getIsFolderColumn().getSelectedValue() != null && getTable()
							.getIsFolderColumn().getSelectedValue() == false)) {
				return true;
			}
			return false;
		}

		protected void prepareNewFile() throws ProcessingException {
		}

		@Order(10.0)
		public class NewFileMenu extends AbstractExtensibleMenu
				implements
					IPermissionGrantedMenu {
			protected Long resourceId;

			@Override
			public String getVisiblePermission() {
				return new CreateFilePermission().getName();
			}

			@Override
			protected void execPrepareAction() throws ProcessingException {
				// create new files only in folders
				setVisible(!selectedResourceIsIFile());

				for (ITreeNode node : getTree().getRootNode().getChildNodes()) {
					if (node.equals(getTree().getSelectedNode())) {
						setVisible(true);
						break;
					}
				}

				if (isVisible()) {
					Long currentUser = SERVICES.getService(
							IUserProcessService.class).getCurrentUserId();

					resourceId = getFolderId();

					if (resourceId == null) {
						// root, only check create file
						setVisibleGranted(ACCESS
								.getLevel(new CreateFilePermission()) > BasicHierarchyPermission.LEVEL_NONE);
					} else {
						// is user authorized to create new files for the
						// choosen folder
						setVisible(SERVICES.getService(
								IPermissionControlService.class).check(
								currentUser, resourceId,
								new CreateFilePermission().getName()));
					}
				}
				prepareNewFile();

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
				FileUploadData data = new FileUploadData();
				data.setParentFolderId(resourceId);
				Activator.getDefault().handle(data);
				reloadPage();
			}
		}

		@Order(20.0)
		public class NewFolderMenu extends AbstractExtensibleMenu
				implements
					IPermissionGrantedMenu {
			protected Long resourceId;

			@Override
			public String getVisiblePermission() {
				return new CreateFolderPermission().getName();
			}

			@Override
			protected void execPrepareAction() throws ProcessingException {
				setVisible(!selectedResourceIsIFile());

				if (getTree().getSelectedNode().equals(
						getTree().getRootNode().getChildNode(0))) {
					setVisible(true);
				}

				if (isVisible()) {

					Long currentUser = SERVICES.getService(
							IUserProcessService.class).getCurrentUserId();

					resourceId = getFolderId();

					if (resourceId == null) {
						// root, only check create file
						setVisibleGranted(ACCESS
								.getLevel(new CreateFolderPermission()) > BasicHierarchyPermission.LEVEL_NONE);
					} else {
						setVisible(SERVICES.getService(
								IPermissionControlService.class)
								.check(currentUser, resourceId,
										getVisiblePermission()));
					}
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
		public class ModifyFolderMenu extends AbstractExtensibleMenu
				implements
					IPermissionGrantedMenu {
			protected Long resourceId;
			protected String folderName;

			@Override
			public String getVisiblePermission() {
				return new UpdateFolderPermission().getName();
			}

			@Override
			protected void execPrepareAction() throws ProcessingException {
				setVisible(!selectedResourceIsIFile());

				if (isVisible()) {
					Long currentUser = SERVICES.getService(
							IUserProcessService.class).getCurrentUserId();

					resourceId = getFolderId();
					folderName = getFolderName();

					setVisible(SERVICES.getService(
							IPermissionControlService.class).check(currentUser,
							resourceId, new UpdateFolderPermission().getName()));
				}
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("ModifyFolder");
			}

			@Override
			protected void execAction() throws ProcessingException {
				FoldersForm form = new FoldersForm();
				form.setFolderId(resourceId);
				form.getNameField().setValue(folderName);
				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(40.0)
		public class DeleteFolderMenu extends AbstractExtensibleMenu
				implements
					IPermissionGrantedMenu {
			protected Long resourceId;

			@Override
			public String getVisiblePermission() {
				return new DeleteFolderPermission().getName();
			}

			@Override
			protected void execPrepareAction() throws ProcessingException {
				setVisible(!selectedResourceIsIFile());

				if (isVisible()) {
					Long currentUser = SERVICES.getService(
							IUserProcessService.class).getCurrentUserId();
					resourceId = getFolderId();
					setVisible(SERVICES.getService(
							IPermissionControlService.class).check(currentUser,
							resourceId, new DeleteFolderPermission().getName()));
				}
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("DeleteFolderMenu");
			}

			@Override
			protected void execAction() throws ProcessingException {
				SERVICES.getService(IFolderService.class)
						.deleteFolderAndChildFolders(resourceId);
				reloadPage();
			}
		}

		@Order(50.0)
		public class AuthorityFolderMenu extends AbstractExtensibleMenu
				implements
					IPermissionGrantedMenu {
			protected Long resourceId;

			@Override
			public String getVisiblePermission() {
				return new FreeFolderPermission().getName();
			}

			@Override
			protected void execPrepareAction() throws ProcessingException {
				setVisible(!selectedResourceIsIFile());

				if (isVisible()) {
					Long currentUser = SERVICES.getService(
							IUserProcessService.class).getCurrentUserId();
					resourceId = getFolderId();
					setVisible(SERVICES.getService(
							IPermissionControlService.class).check(currentUser,
							resourceId, new FreeFolderPermission().getName()));
				}
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("AuthorityFolder");
			}

			@Override
			protected void execAction() throws ProcessingException {
				Map<Long, ArrayList<String>> permissions = SERVICES.getService(
						IFileAndFolderFreeingService.class)
						.getRolePermissionsForFileOrFolder(resourceId);
				AuthorityForm form = new AuthorityForm(resourceId, permissions);
				form.startNew();
				form.waitFor();
			}
		}

		@Order(60.0)
		public class OpenFileMenu extends AbstractExtensibleMenu
				implements
					IPermissionGrantedMenu {

			@Override
			public String getVisiblePermission() {
				return new OpenFilePermission().getName();
			}

			@Override
			protected void execPrepareAction() throws ProcessingException {
				Long currentUser = SERVICES.getService(
						IUserProcessService.class).getCurrentUserId();
				Long resource = getFileFolderIdColumn().getSelectedValue();
				setVisible(SERVICES.getService(IPermissionControlService.class)
						.check(currentUser, resource,
								new OpenFilePermission().getName()));
				if (isVisible())
					setVisible(!getIsFolderColumn().getSelectedValue());
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("OpenFile");
			}

			@Override
			protected void execAction() throws ProcessingException {
				SERVICES.getService(IClientFileService.class).openFile(
						getFileFolderIdColumn().getSelectedValue());
			}
		}

		@Order(70.0)
		public class DeleteMenu extends AbstractExtensibleMenu
				implements
					IPermissionGrantedMenu {

			@Override
			public String getVisiblePermission() {
				return new DeleteFilePermission().getName();
			}

			@Override
			protected void execPrepareAction() throws ProcessingException {
				Long currentUser = SERVICES.getService(
						IUserProcessService.class).getCurrentUserId();
				Long resource = getFileFolderIdColumn().getSelectedValue();
				setVisible(SERVICES.getService(IPermissionControlService.class)
						.check(currentUser, resource,
								new DeleteFilePermission().getName()));
				if (isVisible())
					setVisible(!getIsFolderColumn().getSelectedValue());
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("DeleteFile");
			}

			@Override
			protected void execAction() throws ProcessingException {
				SERVICES.getService(IFileService.class).deleteFile(
						getFileFolderIdColumn().getSelectedValue());
				reloadPage();
			}
		}

		@Order(80.0)
		public class AuthorityMenu extends AbstractExtensibleMenu
				implements
					IPermissionGrantedMenu {

			@Override
			public String getVisiblePermission() {
				return new FreeFilePermission().getName();
			}

			@Override
			protected void execPrepareAction() throws ProcessingException {
				Long currentUser = SERVICES.getService(
						IUserProcessService.class).getCurrentUserId();
				Long resource = getFileFolderIdColumn().getSelectedValue();
				setVisible(SERVICES.getService(IPermissionControlService.class)
						.check(currentUser, resource,
								new FreeFilePermission().getName()));
				if (isVisible())
					setVisible(!getIsFolderColumn().getSelectedValue());
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("ApproveFile");
			}

			@Override
			protected void execAction() throws ProcessingException {
				Long fileId = getFileFolderIdColumn().getSelectedValue();
				Map<Long, ArrayList<String>> permissions = SERVICES.getService(
						IFileAndFolderFreeingService.class)
						.getRolePermissionsForFileOrFolder(fileId);
				AuthorityForm form = new AuthorityForm(fileId, permissions);

				form.startNew();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(90.0)
		public class ModifyMenu extends AbstractExtensibleMenu
				implements
					IPermissionGrantedMenu {

			@Override
			public String getVisiblePermission() {
				return new UpdateFilePermission().getName();
			}

			@Override
			protected void execPrepareAction() throws ProcessingException {
				Long currentUser = SERVICES.getService(
						IUserProcessService.class).getCurrentUserId();
				Long resource = getFileFolderIdColumn().getSelectedValue();
				setVisible(SERVICES.getService(IPermissionControlService.class)
						.check(currentUser, resource,
								new UpdateFilePermission().getName()));
				if (isVisible())
					setVisible(!getIsFolderColumn().getSelectedValue());
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("ModifyFile");
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
		public class DownloadFileMenu extends AbstractExtensibleMenu
				implements
					IPermissionGrantedMenu {

			@Override
			public String getVisiblePermission() {
				return new DownloadFilePermission().getName();
			}

			@Override
			protected void execPrepareAction() throws ProcessingException {
				Long currentUser = SERVICES.getService(
						IUserProcessService.class).getCurrentUserId();
				Long resource = getFileFolderIdColumn().getSelectedValue();
				setVisible(SERVICES.getService(IPermissionControlService.class)
						.check(currentUser, resource,
								new DownloadFilePermission().getName()));
				if (isVisible())
					setVisible(!getIsFolderColumn().getSelectedValue());
			}

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("DownloadFile");
			}

			@Override
			protected void execAction() throws ProcessingException {
				File f = SERVICES.getService(IFileService.class).getServerFile(
						getTable().getFileFolderIdColumn().getSelectedValue());
				String filename = IOUtility.getFileName(f.getAbsolutePath());

				File tempFile;
				if (UserAgentUtility.isWebClient()) {
					tempFile = new File(
							IOUtility.createTempDirectory("download"), filename);
				} else {
					FileChooser chooser = new FileChooser();
					chooser.setTypeLoad(false);
					chooser.setFileName(getNameColumn().getSelectedValue()
							+ "." + IOUtility.getFileExtension(filename));
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

		Long fileId = getTable().getFileFolderIdColumn().getValue(row);

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
		form.getLanguageField()
				.setValue(
						(String) metadata
								.get(CODES
										.getCode(
												DublinCoreMetadataElementSetCodeType.LanguageCode.class)
										.getText()));

		// tags
		form.getAvailableTagsBox().setValue(
				SERVICES.getService(ITagService.class).getTagsForFile(fileId));

		// authority
		form.getAuthorityBox()
				.getRolesField()
				.setValue(
						SERVICES.getService(IFileAndFolderFreeingService.class)
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
