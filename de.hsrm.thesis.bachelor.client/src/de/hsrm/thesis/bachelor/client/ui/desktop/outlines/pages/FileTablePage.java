package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import java.util.List;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.pages.AbstractExtensiblePageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import de.hsrm.testtest.client.FileForm;
import de.hsrm.thesis.bachelor.client.FileChooserForm;
import de.hsrm.thesis.bachelor.shared.services.lookup.FiletypeLookupCall;

public class FileTablePage extends AbstractExtensiblePageWithTable<FileTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("File");
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
      return true;
    }

    @Override
    protected void injectColumnsInternal(List<IColumn<?>> columnList) {
      // TODO Auto-generated method stub
      super.injectColumnsInternal(columnList);
    }

    public FileType0Column getFileType0Column() {
      return getColumnSet().getColumnByClass(FileType0Column.class);
    }

    public FileNrColumn getFileNrColumn() {
      return getColumnSet().getColumnByClass(FileNrColumn.class);
    }

    public TitleColumn getTitleColumn() {
      return getColumnSet().getColumnByClass(TitleColumn.class);
    }

    public TypistColumn getTypistColumn() {
      return getColumnSet().getColumnByClass(TypistColumn.class);
    }

    public FilesizeColumn getFilesizeColumn() {
      return getColumnSet().getColumnByClass(FilesizeColumn.class);
    }

    public FileFormatColumn getFileFormatColumn() {
      return getColumnSet().getColumnByClass(FileFormatColumn.class);
    }

    public MimetypeColumn getMimetypeColumn() {
      return getColumnSet().getColumnByClass(MimetypeColumn.class);
    }

    public AuthorColumn getAuthorColumn() {
      return getColumnSet().getColumnByClass(AuthorColumn.class);
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
    public class FileType0Column extends AbstractSmartColumn<Long> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FileType0");
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

    @Order(50.0)
    public class TitleColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Title");
      }
    }

    @Order(60.0)
    public class AuthorColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Author");
      }
    }

    @Order(70.0)
    public class TypistColumn extends AbstractSmartColumn<Long> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Typist");
      }
    }

    @Order(80.0)
    public class FilesizeColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Filesize");
      }
    }

    @Order(90.0)
    public class FileFormatColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FileFormat");
      }
    }

    @Order(100.0)
    public class MimetypeColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Mimetype");
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
        FileChooserForm form = new FileChooserForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }

        FileForm frm = new FileForm();
        frm.startNew();
        frm.waitFor();
        if (frm.isFormStored()) {
          reloadPage();
        }
      }
    }
  }
}
