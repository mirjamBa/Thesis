package de.hsrm.thesis.filemanagement.client.ui.desktop.outlines.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractValueField;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.IDateField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.IStringField;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.pages.AbstractExtensiblePageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.administration.client.ui.forms.FileFormatForm;
import de.hsrm.mi.administration.shared.services.IFileFormatService;
import de.hsrm.mi.administration.shared.services.IMetadataService;
import de.hsrm.mi.administration.shared.services.ITagService;
import de.hsrm.mi.administration.shared.services.code.DatatypeCodeType;
import de.hsrm.thesis.bachelor.shared.column.ColumnSpec;
import de.hsrm.thesis.bachelor.shared.services.lookup.UserLookupCall;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileChooserForm;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm;
import de.hsrm.thesis.filemanagement.client.ui.forms.FiletypeChooserForm;
import de.hsrm.thesis.filemanagement.client.util.ColumnUtility;
import de.hsrm.thesis.filemanagement.shared.Icons;
import de.hsrm.thesis.filemanagement.shared.files.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.formdata.FileSearchFormData;
import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.IRoleProcessService;
import de.hsrm.thesis.filemanagement.shared.services.IUserProcessService;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;
import de.hsrm.thesis.filemanagement.shared.utility.ArrayUtility;

public class FileTablePage extends AbstractExtensiblePageWithTable<FileTablePage.FileTable> {
	private List<IColumn<?>> m_injectedColumns;

	public FileTablePage() {
		super();
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("File");
	}

	@Override
	protected String getConfiguredIconId() {
		return Icons.Folder;
	}

	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}

	private Long[] extractIdsFromTableData(Object[][] data) {
		Long[] ids = new Long[data.length];
		for (int i = 0; i < data.length; i++) {
			ids[i] = (Long) data[i][0];
		}
		return ids;
	}

	@Override
	protected Object[][] execLoadTableData(SearchFilter filter) throws ProcessingException {
		Object[][] files = SERVICES.getService(IFileService.class).getFiles((FileSearchFormData) filter.getFormData());

		String[] columnNames = new String[] { TEXTS.get("Title"), TEXTS.get("Filesize"), TEXTS.get("EntryDate"), };

		Object[][] columns = SERVICES.getService(IMetadataService.class).getMetadataForFiles(
				extractIdsFromTableData(files), columnNames);

		Object[][] all = ArrayUtility.concatArrays(files, columns);

		ColumnSpec[] columnSpecs = new ColumnSpec[] {
				new ColumnSpec(((Integer) TEXTS.get("Titel").hashCode()).toString(), TEXTS.get("Title"),
						DatatypeCodeType.StringCode.ID),
				new ColumnSpec(((Integer) TEXTS.get("Filesize").hashCode()).toString(), TEXTS.get("Filesize"),
						DatatypeCodeType.StringCode.ID),
				new ColumnSpec(((Integer) TEXTS.get("EntryDate").hashCode()).toString(), TEXTS.get("EntryDate"),
						DatatypeCodeType.DateCode.ID), };

		updateDynamicColumns(columnSpecs);

		return all;

	}

	private void updateDynamicColumns(ColumnSpec[] columnSpecs) throws ProcessingException {
		if (columnSpecs.length == 0) {
			return;
		}
		FileTable table = getTable();
		m_injectedColumns = new ArrayList<IColumn<?>>();
		for (ColumnSpec spec : columnSpecs) {
			if (spec.getType().equals(DatatypeCodeType.StringCode.ID)) {
				m_injectedColumns.add(ColumnUtility.createDynamicStringColumn(spec.getId(), spec.getText()));
			} else if (spec.getType().equals(DatatypeCodeType.DateCode.ID)) {
				m_injectedColumns.add(ColumnUtility.createDynamicDateColumn(spec.getId(), spec.getText()));
			} else if (spec.getType().equals(DatatypeCodeType.LongCode.ID)) {
				m_injectedColumns.add(ColumnUtility.createDynamicLongColumn(spec.getId(), spec.getText()));
			} else if (spec.getType().equals(DatatypeCodeType.DoubleCode.ID)) {
				m_injectedColumns.add(ColumnUtility.createDynamicDoubleColumn(spec.getId(), spec.getText()));
			}
		}
		table.resetColumnConfiguration();
	}

	@Order(10.0)
	public class FileTable extends AbstractExtensibleTable {

		@Override
		protected boolean getConfiguredAutoResizeColumns() {
			return true;
		}

		@Override
		protected void injectColumnsInternal(List<IColumn<?>> columnList) {
			if (m_injectedColumns != null) {
				columnList.addAll(m_injectedColumns);
			}
		}

		public FileTypeColumn getFileTypeColumn() {
			return getColumnSet().getColumnByClass(FileTypeColumn.class);
		}

		public FileNrColumn getFileNrColumn() {
			return getColumnSet().getColumnByClass(FileNrColumn.class);
		}

		public TypistColumn getTypistColumn() {
			return getColumnSet().getColumnByClass(TypistColumn.class);
		}

		public FilePathColumn getFilePathColumn() {
			return getColumnSet().getColumnByClass(FilePathColumn.class);
		}

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
		public class FileTypeColumn extends AbstractSmartColumn<Long> {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("FileType");
			}

			@Override
			protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
				return FileTypeCodeType.class;
			}

			@Override
			protected int getConfiguredWidth() {
				return 200;
			}
		}

		@Order(30.0)
		public class FileNrColumn extends AbstractLongColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("FileNr");
			}

			@Override
			protected int getConfiguredWidth() {
				return 120;
			}
		}

		@Order(40.0)
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

		@Order(50.0)
		public class FilePathColumn extends AbstractStringColumn {

			@Override
			protected boolean getConfiguredDisplayable() {
				return false;
			}

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("FilePath");
			}

			@Override
			protected boolean getConfiguredVisible() {
				return false;
			}
		}

		@Order(10.0)
		public class NewFileMenu extends AbstractExtensibleMenu {

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
				IFileFormatService fileFormatService = SERVICES.getService(IFileFormatService.class);

				// TODO make flexible (chain of responsibility?)

				// choose file from filesystem
				FileChooserForm form = new FileChooserForm();
				form.startNew();
				form.waitFor();
				if (form.isFormStored()) {

					// extract data
					ServerFileData fileData = form.getFileData();

					// check for operation cancelled //FIXME
					// if (form.getFileData() != null) {
					String fileformat = fileData.getFileformat();
					Long filetypeNr = null;

					// check if format is registered and linked with
					// filetype
					if (!fileFormatService.isFileformatRegistered(fileformat)) {
						// force to register fileformat
						// TODO
						FileFormatForm fileformatForm = new FileFormatForm();
						fileformatForm.getFileFormatField().setValue(fileformat);
						fileformatForm.getFileFormatField().setEnabled(false);
						fileformatForm.startNew();
						fileformatForm.waitFor();
						if (fileformatForm.isFormStored()) {
							reloadPage();
						}
					} else {
						// check if fileformat is assigned to filetype
						// multiple
						if (fileFormatService.isFormatMultipleAssigned(fileformat)) {
							// force choosing one of the assigned filetypes
							FiletypeChooserForm ft = new FiletypeChooserForm();
							ft.setFileformat(fileformat);
							ft.startNew();
							ft.waitFor();
							if (ft.isFormStored()) {
								reloadPage();
							}
							filetypeNr = ft.getFiletypeNr();
						}
					}

					// extract filetype
					if (filetypeNr == null) {
						filetypeNr = fileFormatService.getFiletypeForFileFormat(fileformat);
					}

					// open and prepare fileform
					FileForm frm = new FileForm(form.getFileData(), filetypeNr);

					frm.getTypistField().setValue(SERVICES.getService(IUserProcessService.class).getCurrentUserId());
					frm.getFileExtensionField().setValue(fileData.getFileExtension());
					frm.getFileTypeField().setValue(filetypeNr);
					frm.getFileTypeField().setEnabled(false);
					frm.getCreationDateField().setValue(new Date());
					frm.getFilesizeField().setValue(form.getFileData().getFilesize());

					frm.startNew();
					frm.waitFor();
					if (frm.isFormStored()) {
						reloadPage();
					}
					// }
				}
			}
		}

		@Order(20.0)
		public class OpenFileMenu extends AbstractExtensibleMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("OpenFile");
			}

			@Override
			protected void execAction() throws ProcessingException {
				SERVICES.getService(IFileService.class).openFile(getFileIdColumn().getSelectedValue());
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
				SERVICES.getService(IFileService.class).deleteFile(getFileIdColumn().getSelectedValue());
				reloadPage();
			}
		}

		@Order(40.0)
		public class AuthorityMenu extends AbstractExtensibleMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Unfreeze");
			}

			@Override
			protected void execAction() throws ProcessingException {
				FileForm form = new FileForm(getFileIdColumn().getSelectedValue());

				form.getMetadataBox().setVisible(false);
				form.getDetailedBox().setVisible(false);
				form.getTagBox().setVisible(false);

				form.getAuthorityBox()
						.getRolesField()
						.setValue(
								SERVICES.getService(IRoleProcessService.class).getApprovedRolesForFile(
										getFileIdColumn().getSelectedValue()));

				form.startUpdateAuthorities();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}

		@Order(50.0)
		public class ModifyMenu extends AbstractExtensibleMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Modify");
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void execAction() throws ProcessingException {
				IMetadataService service = SERVICES.getService(IMetadataService.class);
				Long fileId = getFileIdColumn().getSelectedValue();

				FileForm form = new FileForm(fileId);

				Map<String, Object> metadata = service.getMetdataMapForFile(fileId, getFileTypeColumn()
						.getSelectedValue());

				// metadata
				form.getTypistField().setValue(getTypistColumn().getSelectedValue());
				form.getFilesizeField().setValue((String) metadata.get(form.getFilesizeField().getLabel()));
				form.getCreationDateField().setValue((Date) metadata.get(form.getCreationDateField().getLabel()));
				form.getFileExtensionField().setValue((String) metadata.get(form.getFileExtensionField().getLabel()));

				// DCMI metadata
				IFormField[] fields = form.getDCMIBox().getFields();
				for (IFormField field : fields) {
					String label = field.getLabel();
					Object value = metadata.get(label);
					if (field instanceof AbstractValueField<?>) {
						field = (AbstractValueField<?>) field;
						if (field instanceof IStringField) {
							((AbstractValueField<String>) field).setValue((String) value);
						} else if (field instanceof IDateField) {
							((AbstractValueField<Date>) field).setValue((Date) value);
						}

					}
				}
				form.setFiletypeNr(getFileTypeColumn().getSelectedValue());
				form.getFileTypeField().setValue(getFileTypeColumn().getSelectedValue());

				// tags
				form.getAvailableTagsBox().setValue(SERVICES.getService(ITagService.class).getTagsForFile(fileId));

				// authority
				form.getAuthorityBox().getRolesField()
						.setValue(SERVICES.getService(IRoleProcessService.class).getApprovedRolesForFile(fileId));

				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}
		}
	}

	@Override
	protected Class<? extends ISearchForm> getConfiguredSearchForm() {
		return FileSearchForm.class;
	}
}
