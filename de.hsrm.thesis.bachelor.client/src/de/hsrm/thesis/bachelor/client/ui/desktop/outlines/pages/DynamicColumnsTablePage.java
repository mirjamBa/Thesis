//package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.eclipse.scout.commons.annotations.Order;
//import org.eclipse.scout.commons.exception.ProcessingException;
//import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
//import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
//import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDoubleColumn;
//import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
//import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
//import org.eclipse.scout.rt.shared.AbstractIcons;
//import org.eclipse.scout.rt.shared.TEXTS;
//import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
//import org.eclipse.scout.service.SERVICES;
//
//public class DynamicColumnsTablePage extends AbstractPageWithTable<DynamicColumnsTablePage.Table> {
//  private List<IColumn<?>> m_injectedColumns;
//
//  @Override
//  protected String getConfiguredIconId() {
//    return AbstractIcons.TreeNode;
//  }
//
//  @Override
//  protected boolean getConfiguredLeaf() {
//    return true;
//  }
//
////  @Override
////  protected Class<? extends ISearchForm> getConfiguredSearchForm() {
////    return DynamicDataSearchForm.class;
////  }
//
//  @Override
//  protected boolean getConfiguredSearchRequired() {
//    return true;
//  }
//
//  @Override
//  protected String getConfiguredTitle() {
//    return TEXTS.get("DynamicColumns");
//  }
//
//  @Override
//  protected Object[][] execLoadTableData(SearchFilter filter) throws ProcessingException {
//    DynamicDataSearchFormData formData = (DynamicDataSearchFormData) filter.getFormData();
//    DynamicDataMatrixData matrixData = SERVICES.getService(IDynamicDataPageService.class).getStatisticsMatrixData(formData);
//    if (matrixData == null) {
//      return new Object[0][];
//    }
//    //create dynamic columns based on matrix data assuming matrixData has a property "getColumnSpecs" with  bean ColumnSpec
//
//    updateDynamicColumns(matrixData.getColumnSpecs());
//
//    //fill rows
//    return matrixData.getRows();
//  }
//
//  private void updateDynamicColumns(ColumnSpec[] columnSpecs) throws ProcessingException {
//    if (columnSpecs.length == 0) {
//      return;
//    }
//    Table table = getTable();
//    m_injectedColumns = new ArrayList<IColumn<?>>();
//    for (ColumnSpec spec : columnSpecs) {
//      //XXX switch on spec.getType() or something like that...
//      m_injectedColumns.add(createDynamicDoubleColumn(spec.getId(), spec.getText()));
//      //...
//    }
//    table.resetColumnConfiguration();
//  }
//
//  private IColumn<?> createDynamicDoubleColumn(final String columnId, final String label) {
//    return new AbstractDoubleColumn() {
//      @Override
//      protected String getConfiguredHeaderText() {
//        return label;
//      }
//
//      @Override
//      public String getColumnId() {
//        return columnId;
//      }
//
//      @Override
//      protected int getConfiguredWidth() {
//        return 120;
//      }
//
//      @Override
//      protected Double execParseValue(ITableRow row, Object rawValue) throws ProcessingException {
//        rawValue = preprocessDynamicColumnsCell(this, row, rawValue);
//        return super.execParseValue(row, rawValue);
//      }
//    };
//  }
//
//  /**
//   * @return {@link DecoratedValue#getValue()} after decorating the table cell
//   */
//  private Object preprocessDynamicColumnsCell(IColumn<?> col, ITableRow row, Object rawValue) throws ProcessingException {
//    DecoratedValue rawCell = (DecoratedValue) rawValue;
//    if (rawCell != null) {
//      row.getCellForUpdate(col).setBackgroundColor(rawCell.getColor());
//      return rawCell.getValue();
//    }
//    return rawValue;
//  }
//
//  @Order(10.0f)
//  public class Table extends AbstractTable {
//
//    @Override
//    protected boolean getConfiguredSortEnabled() {
//      return false;
//    }
//
//    @Override
//    protected void injectColumnsInternal(List<IColumn<?>> columnList) {
//      if (m_injectedColumns != null) {
//        columnList.addAll(m_injectedColumns);
//      }
//    }
//  }
//
//}
