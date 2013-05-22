package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;

public class MetadataTablePage extends AbstractPageWithTable<MetadataTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Metadata");
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {
  }
}
