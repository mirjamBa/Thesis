/**
 * 
 */
package de.hsrm.perfunctio.core.client.ui;

import java.util.Date;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDoubleColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractObjectColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.shared.services.common.code.CODES;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

import de.hsrm.perfunctio.core.shared.services.code.DatatypeCodeType;
import de.hsrm.perfunctio.core.shared.services.code.DublinCoreMetadataElementSetCodeType;
import de.hsrm.perfunctio.core.shared.services.code.FileTypeCodeType;

public class DatatypeColumnFactory extends ColumnFactory {

	@Override
	public IColumn<?> createColumn(Long datatypeId, final String columnId,
			final String label) {
		ICode<Long> code = CODES
				.getCode(DublinCoreMetadataElementSetCodeType.TypeCode.class);
		if (columnId.equals(((Integer) code.getText().hashCode()).toString())) {
			return  createDynamicFileTypeSmartColumn(columnId, label);
		} else if (datatypeId.equals(DatatypeCodeType.StringCode.ID)) {
			return createDynamicStringColumn(columnId, label);
		} else if (datatypeId.equals(DatatypeCodeType.DateCode.ID)) {
			return createDynamicDateColumn(columnId, label);
		} else if (datatypeId.equals(DatatypeCodeType.LongCode.ID)) {
			return createDynamicLongColumn(columnId, label);
		} else if (datatypeId.equals(DatatypeCodeType.DoubleCode.ID)) {
			return createDynamicDoubleColumn(columnId, label);
		} else {
			return createDynamicObjectColumn(columnId, label);
		}
	}
	private IColumn<?> createDynamicFileTypeSmartColumn(final String columnId,
			final String label) {
		return new AbstractSmartColumn<Long>() {
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
			protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
				return FileTypeCodeType.class;
			}
		};
	}

	private IColumn<?> createDynamicStringColumn(final String columnId,
			final String label) {
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
			protected String execParseValue(ITableRow row, Object rawValue)
					throws ProcessingException {
				return super.execParseValue(row, rawValue);
			}

		};
	}

	private IColumn<?> createDynamicDateColumn(final String columnId,
			final String label) {
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
			protected Date execParseValue(ITableRow row, Object rawValue)
					throws ProcessingException {
				return super.execParseValue(row, rawValue);
			}

		};
	}

	private IColumn<?> createDynamicLongColumn(final String columnId,
			final String label) {
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
			protected Long execParseValue(ITableRow row, Object rawValue)
					throws ProcessingException {
				return super.execParseValue(row, rawValue);
			}

		};
	}

	private IColumn<?> createDynamicDoubleColumn(final String columnId,
			final String label) {
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
			protected Double execParseValue(ITableRow row, Object rawValue)
					throws ProcessingException {
				return super.execParseValue(row, rawValue);
			}

		};
	}

	private IColumn<?> createDynamicObjectColumn(final String columnId,
			final String label) {
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
			protected Object execParseValue(ITableRow row, Object rawValue)
					throws ProcessingException {
				return super.execParseValue(row, rawValue);
			}

		};
	}

}
