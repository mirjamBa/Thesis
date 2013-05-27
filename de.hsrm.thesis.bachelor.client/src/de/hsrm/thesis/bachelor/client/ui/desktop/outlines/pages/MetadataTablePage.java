package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import de.hsrm.thesis.bachelor.client.MetadataForm;
import de.hsrm.thesis.bachelor.shared.services.code.DatatypeCodeType;
import de.hsrm.thesis.bachelor.shared.services.lookup.FiletypeLookupCall;

public class MetadataTablePage extends AbstractPageWithTable<MetadataTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Metadata");
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public DatatypeColumn getDatatypeColumn() {
      return getColumnSet().getColumnByClass(DatatypeColumn.class);
    }

    public FileType0Column getFileType0Column() {
      return getColumnSet().getColumnByClass(FileType0Column.class);
    }

    public AttributIDColumn getAttributIDColumn() {
      return getColumnSet().getColumnByClass(AttributIDColumn.class);
    }

    @Order(10.0)
    public class AttributIDColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("AttributID");
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }
    }

    @Order(20.0)
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }
    }

    @Order(30.0)
    public class DatatypeColumn extends AbstractSmartColumn<Long> {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Datatype");
      }

      @Override
      protected Class<? extends ICodeType> getConfiguredCodeType() {
        return DatatypeCodeType.class;

      }
    }

    @Order(40.0)
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

    @Order(10.0)
    public class NewMetadataattributeMenu extends AbstractExtensibleMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewMetadataattribute");
      }

      @Override
      protected boolean getConfiguredEmptySpaceAction() {
        return true;
      }

      @Override
      protected void execAction() throws ProcessingException {
        MetadataForm form = new MetadataForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
  }
}
