/**
 * 
 */
package de.hsrm.thesis.filemanagement.client.util;

import java.util.Date;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDoubleColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractObjectColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;

/**
 * @author mba
 */
public class ColumnUtility {

	public static IColumn<?> createDynamicStringColumn(final String columnId, final String label) {
		return new AbstractStringColumn() {
			@Override
			protected String getConfiguredHeaderText() {
				return label;
			}

			@Override
			public String getColumnId() {
				return columnId;
			}

			@Override
			protected int getConfiguredWidth() {
				return 120;
			}

			@Override
			protected String execParseValue(ITableRow row, Object rawValue) throws ProcessingException {
				return super.execParseValue(row, rawValue);
			}

		};
	}

	public static IColumn<?> createDynamicDateColumn(final String columnId, final String label) {
		return new AbstractDateColumn() {
			@Override
			protected String getConfiguredHeaderText() {
				return label;
			}

			@Override
			public String getColumnId() {
				return columnId;
			}

			@Override
			protected int getConfiguredWidth() {
				return 120;
			}

			@Override
			protected Date execParseValue(ITableRow row, Object rawValue) throws ProcessingException {
				return super.execParseValue(row, rawValue);
			}

		};
	}
	
	public static IColumn<?> createDynamicLongColumn(final String columnId, final String label) {
    return new AbstractLongColumn() {
      @Override
      protected String getConfiguredHeaderText() {
        return label;
      }

      @Override
      public String getColumnId() {
        return columnId;
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }

      @Override
      protected Long execParseValue(ITableRow row, Object rawValue) throws ProcessingException {
        return super.execParseValue(row, rawValue);
      }

    };
  }
	
	public static IColumn<?> createDynamicDoubleColumn(final String columnId, final String label) {
    return new AbstractDoubleColumn() {
      @Override
      protected String getConfiguredHeaderText() {
        return label;
      }

      @Override
      public String getColumnId() {
        return columnId;
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }

      @Override
      protected Double execParseValue(ITableRow row, Object rawValue) throws ProcessingException {
        return super.execParseValue(row, rawValue);
      }

    };
  }
	
	public static IColumn<?> createDynamicObjectColumn(final String columnId, final String label) {
		return new AbstractObjectColumn() {
		
			@Override
			protected String getConfiguredHeaderText() {
				return label;
			}

			@Override
			public String getColumnId() {
				return columnId;
			}

			@Override
			protected int getConfiguredWidth() {
				return 120;
			}

			@Override
			protected Object execParseValue(ITableRow row, Object rawValue) throws ProcessingException {
				return super.execParseValue(row, rawValue);
			}

		};
	}

}
