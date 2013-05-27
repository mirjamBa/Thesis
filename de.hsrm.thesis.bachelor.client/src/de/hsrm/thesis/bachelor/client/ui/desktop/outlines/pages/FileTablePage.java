package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.pages.AbstractExtensiblePageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.thesis.bachelor.client.FileChooserForm;
import de.hsrm.thesis.bachelor.client.FileForm;

public class FileTablePage extends AbstractExtensiblePageWithTable<FileTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("File");
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {

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
