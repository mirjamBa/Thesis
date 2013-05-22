package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;

public class TagTablePage extends AbstractPageWithTable<TagTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Tag");
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {

    public TagNameColumn getTagNameColumn() {
      return getColumnSet().getColumnByClass(TagNameColumn.class);
    }

    @Order(10.0)
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
  }
}
