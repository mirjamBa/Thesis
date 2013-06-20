package de.hsrm.mi.administration.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.administration.client.ui.forms.TagForm;
import de.hsrm.thesis.filemanagement.shared.services.ITagService;

public class TagTablePage extends AbstractPageWithTable<TagTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Tag");
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected Object[][] execLoadTableData(SearchFilter filter) throws ProcessingException {
    return SERVICES.getService(ITagService.class).getTags();
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {

    public TagIdColumn getTagIdColumn() {
      return getColumnSet().getColumnByClass(TagIdColumn.class);
    }

    public TagNameColumn getTagNameColumn() {
      return getColumnSet().getColumnByClass(TagNameColumn.class);
    }

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
      return true;
    }

    @Order(10.0)
    public class TagIdColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("TagId");
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }
    }

    @Order(20.0)
    public class TagNameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("TagName");
      }

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
      }
    }

    @Order(10.0)
    public class NewTagMenu extends AbstractExtensibleMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewTag");
      }

      @Override
      protected boolean getConfiguredEmptySpaceAction() {
        return true;
      }

      @Override
      protected void execAction() throws ProcessingException {
        TagForm form = new TagForm();
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
        TagForm form = new TagForm();
        form.getTagNameField().setValue(getTagNameColumn().getSelectedValue());
        form.getTagIdField().setValue(getTagIdColumn().getSelectedValue());
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
      protected boolean getConfiguredMultiSelectionAction() {
        return true;
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("DeleteMenu");
      }

      @Override
      protected void execAction() throws ProcessingException {
        if (MessageBox.showDeleteConfirmationMessage(TEXTS.get("Tag"), getTagNameColumn().getValues(getSelectedRows()))) {
          SERVICES.getService(ITagService.class).deleteTag(getTagIdColumn().getValues(getSelectedRows()));
          reloadPage();
        }
      }
    }
  }
}
