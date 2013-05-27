package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.client.VersionForm;
import de.hsrm.thesis.bachelor.shared.IVersionService;

public class VersionTablePage extends AbstractPageWithTable<VersionTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Version");
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected Object[][] execLoadTableData(SearchFilter filter) throws ProcessingException {
    return SERVICES.getService(IVersionService.class).getVersionControlOfFiletypes();
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {

    public VersionColumn getVersionColumn() {
      return getColumnSet().getColumnByClass(VersionColumn.class);
    }

    public FiletypeIdColumn getFiletypeIdColumn() {
      return getColumnSet().getColumnByClass(FiletypeIdColumn.class);
    }

    public FileType0Column getFileType0Column() {
      return getColumnSet().getColumnByClass(FileType0Column.class);
    }

    @Order(10.0)
    public class FiletypeIdColumn extends AbstractLongColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FiletypeId");
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(20.0)
    public class FileType0Column extends AbstractStringColumn {

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

    @Order(10.0)
    public class EditMenu extends AbstractExtensibleMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Edit");
      }

      @Override
      protected void execAction() throws ProcessingException {
        VersionForm form = new VersionForm();
        form.getFileType0Field().setValue(getFiletypeIdColumn().getSelectedValue());
        form.getVersionField().setValue(getVersionColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
  }
}
