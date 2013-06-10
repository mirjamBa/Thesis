package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.pages.AbstractExtensiblePageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.administration.client.ui.forms.FileFormatForm;
import de.hsrm.mi.administration.shared.services.IFileFormatService;
import de.hsrm.mi.administration.shared.services.lookup.FiletypeLookupCall;
import de.hsrm.thesis.bachelor.client.FileChooserForm;
import de.hsrm.thesis.bachelor.client.FileForm;
import de.hsrm.thesis.bachelor.client.FileSearchForm;
import de.hsrm.thesis.bachelor.client.FiletypeChooserForm;
import de.hsrm.thesis.bachelor.shared.FileSearchFormData;
import de.hsrm.thesis.bachelor.shared.IFileService;
import de.hsrm.thesis.bachelor.shared.column.ColumnSpec;
import de.hsrm.thesis.bachelor.shared.files.ServerFileData;
import de.hsrm.thesis.bachelor.shared.services.IRoleProcessService;
import de.hsrm.thesis.bachelor.shared.services.lookup.UserLookupCall;

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
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected Object[][] execLoadTableData(SearchFilter filter) throws ProcessingException {
    //    Object[][] o = new Object[][]{{123L, 0L, 1234, "Titel", "Autor", "nochwas", null},
    //        {123L, 0L, 1234, "Titel", "Autor", null, "was anderes"}};
    //
    //    ColumnSpec[] columnSpecs = new ColumnSpec[]{new ColumnSpec("1234", "1.Spalte", "String"), new ColumnSpec("678", "2.Spalte", "String")};
    //    updateDynamicColumns(columnSpecs);
    //    
    //    return o;

    return SERVICES.getService(IFileService.class).getFiles((FileSearchFormData) filter.getFormData());

  }

  private void updateDynamicColumns(ColumnSpec[] columnSpecs) throws ProcessingException {
    if (columnSpecs.length == 0) {
      return;
    }
    FileTable table = getTable();
    m_injectedColumns = new ArrayList<IColumn<?>>();
    for (ColumnSpec spec : columnSpecs) {
      //XXX switch on spec.getType() or something like that...
      m_injectedColumns.add(createDynamicStringColumn(spec.getId(), spec.getText()));
      //...
    }
    table.resetColumnConfiguration();
  }

  private IColumn<?> createDynamicStringColumn(final String columnId, final String label) {
    return new AbstractStringColumn() {
      @Override
      protected String getConfiguredHeaderText() {
        return label;
      }

      @Override
      public String getColumnId() {
        return columnId;
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }

      @Override
      protected String execParseValue(ITableRow row, Object rawValue) throws ProcessingException {
        return super.execParseValue(row, rawValue);
      }

    };
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
      protected Class<? extends LookupCall> getConfiguredLookupCall() {
        return FiletypeLookupCall.class;

      }
    }

    @Order(30.0)
    public class FileNrColumn extends AbstractLongColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FileNr");
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

        //choose file from filesystem
        FileChooserForm form = new FileChooserForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }

        //extract data
        ServerFileData fileData = form.getFileData();

        //check for operation cancelled //FIXME 
        if (form.getFileData() != null) {
          String fileformat = fileData.getFileformat();
          Long filetypeNr = null;

          //check if format is registered and linked with filetype
          if (!fileFormatService.isFileformatRegistered(fileformat)) {
            //force to register fileformat
            //TODO
            FileFormatForm fileformatForm = new FileFormatForm();
            fileformatForm.getFileFormatField().setValue(fileformat);
            fileformatForm.getFileFormatField().setEnabled(false);
            fileformatForm.startNew();
            fileformatForm.waitFor();
            if (fileformatForm.isFormStored()) {
              reloadPage();
            }
          }
          else {
            //check if fileformat is assigned to filetype multiple
            if (fileFormatService.isFormatMultipleAssigned(fileformat)) {
              //force choosing one of the assigned filetypes
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

          //extract filetype 
          if (filetypeNr == null) {
            filetypeNr = fileFormatService.getFiletypeForFileFormat(fileformat);
          }

          //open and prepare fileform
          FileForm frm = new FileForm(form.getFileData(), filetypeNr);

          //          frm.getTypistField().setValue(SERVICES.getService(IUserProcessService.class).getUserId(ClientSession.get().getUserId()));
          //          frm.getTypistField().setEnabled(false);
          frm.getFileTypeField().setValue(filetypeNr);
          frm.getFileTypeField().setEnabled(false);
          frm.getCreationDateField().setValue(new Date());
          frm.getCreationDateField().setEnabled(false);
          frm.getFilesizeField().setValue(form.getFileData().getFilesize());
          frm.getFilesizeField().setEnabled(false);

          frm.startNew();
          frm.waitFor();
          if (frm.isFormStored()) {
            reloadPage();
          }
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
        form.getRemarkBox().setVisible(false);

        form.getAuthorityBox().getRolesField().setValue(SERVICES.getService(IRoleProcessService.class).getApprovedRolesForFile(getFileIdColumn().getSelectedValue()));

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

      @Override
      protected void execAction() throws ProcessingException {
        FileForm form = new FileForm(getFileIdColumn().getSelectedValue());

        form.getTypistField().setValue(getTypistColumn().getSelectedValue());
        form.getFileTypeField().setValue(getFileTypeColumn().getSelectedValue());

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
