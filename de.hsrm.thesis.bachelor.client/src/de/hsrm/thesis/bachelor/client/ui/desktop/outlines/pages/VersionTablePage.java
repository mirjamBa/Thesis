package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;

public class VersionTablePage extends AbstractPageWithTable<VersionTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Version");
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {

    public VersionColumn getVersionColumn() {
      return getColumnSet().getColumnByClass(VersionColumn.class);
    }

    public FileType0Column getFileType0Column() {
      return getColumnSet().getColumnByClass(FileType0Column.class);
    }

    @Order(10.0)
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

    @Order(20.0)
    public class VersionColumn extends AbstractBooleanColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Version");
      }

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
      }
    }
  }
}
