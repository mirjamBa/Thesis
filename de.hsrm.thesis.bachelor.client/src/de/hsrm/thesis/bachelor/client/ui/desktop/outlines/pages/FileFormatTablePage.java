package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.thesis.bachelor.client.FileFormat0Form;

public class FileFormatTablePage extends AbstractPageWithTable<FileFormatTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("FileType");
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {

    public FileType0Column getFileType0Column() {
      return getColumnSet().getColumnByClass(FileType0Column.class);
    }

    public IDColumn getIDColumn() {
      return getColumnSet().getColumnByClass(IDColumn.class);
    }

    public FileFormatColumn getFileFormatColumn() {
      return getColumnSet().getColumnByClass(FileFormatColumn.class);
    }

    @Order(10.0)
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

    @Order(20.0)
    public class FileType0Column extends AbstractSmartColumn<Long> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FileType0");
      }

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
      }
    }

    @Order(30.0)
    public class IDColumn extends AbstractLongColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ID");
      }

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
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
        FileFormat0Form form = new FileFormat0Form();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }

      }
    }
  }
}
