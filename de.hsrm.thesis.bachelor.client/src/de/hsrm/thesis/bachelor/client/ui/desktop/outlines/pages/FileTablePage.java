package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.pages.AbstractExtensiblePageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;

public class FileTablePage extends AbstractExtensiblePageWithTable<FileTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("File");
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {
  }
}
