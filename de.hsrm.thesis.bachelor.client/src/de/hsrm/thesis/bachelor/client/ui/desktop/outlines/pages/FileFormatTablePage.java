package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.client.FileFormatForm;
import de.hsrm.thesis.bachelor.shared.IFileFormatService;
import de.hsrm.thesis.bachelor.shared.services.lookup.FiletypeLookupCall;

public class FileFormatTablePage extends AbstractPageWithTable<FileFormatTablePage.Table> {

  private long m_fileformatNr;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("FileType");
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected Object[][] execLoadTableData(SearchFilter filter) throws ProcessingException {
    return SERVICES.getService(IFileFormatService.class).getFileFormats(getFileformatNr());
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {

    public FileTypeColumn getFileTypeColumn() {
      return getColumnSet().getColumnByClass(FileTypeColumn.class);
    }

    public IdColumn getIDColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    public FileFormatColumn getFileFormatColumn() {
      return getColumnSet().getColumnByClass(FileFormatColumn.class);
    }

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
      return true;
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
      protected Class<? extends LookupCall> getConfiguredLookupCall() {
        return FiletypeLookupCall.class;
      }
    }

    @Order(10.0)
    public class NewFormatMenu extends AbstractMenu {

      @Override
      protected boolean getConfiguredEmptySpaceAction() {
        return true;
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewFormat");
      }

      @Override
      protected void execAction() throws ProcessingException {
        FileFormatForm form = new FileFormatForm();
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
        form.getFileFormatField().setValue(getFileFormatColumn().getSelectedValue());
        form.getFileTypeField().setValue(getFileTypeColumn().getSelectedValue());
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
        if (MessageBox.showDeleteConfirmationMessage(TEXTS.get("FileType"), getFileFormatColumn().getValues(getSelectedRows()))) {
          SERVICES.getService(IFileFormatService.class).delete(getIDColumn().getValues(getSelectedRows()));
          reloadPage();
        }
      }
    }
  }

  @FormData
  public long getFileformatNr() {
    return m_fileformatNr;
  }

  @FormData
  public void setFileformatNr(long fileformatNr) {
    m_fileformatNr = fileformatNr;
  }
}
