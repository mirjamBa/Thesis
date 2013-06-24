package de.hsrm.thesis.filemanagement.client.ui.forms;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractValueField;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

import de.hsrm.thesis.filemanagement.client.ui.DatatypeFormFieldFactory;
import de.hsrm.thesis.filemanagement.client.ui.FormFieldFactory;
import de.hsrm.thesis.filemanagement.shared.services.code.DatatypeCodeType;

public class MetadataTableField extends AbstractTableField<MetadataTableField.Table> {

	@Override
	protected String getConfiguredLabel() {
		return TEXTS.get("Metadata");
	}

	@Order(10.0)
	public class Table extends AbstractExtensibleTable {

		@Override
		protected boolean getConfiguredAutoResizeColumns() {
			return true;
		}

		public AttributeColumn getAttributeColumn() {
			return getColumnSet().getColumnByClass(AttributeColumn.class);
		}

		public ValueColumn getValueColumn() {
			return getColumnSet().getColumnByClass(ValueColumn.class);
		}

		public DatatypeColumn getDatatypeColumn() {
			return getColumnSet().getColumnByClass(DatatypeColumn.class);
		}

		public AttributIDColumn getAttributIDColumn() {
			return getColumnSet().getColumnByClass(AttributIDColumn.class);
		}

		@Order(10.0)
		public class AttributIDColumn extends AbstractLongColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("AttributID");
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
		public class AttributeColumn extends AbstractStringColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Attribute");
			}
		}

		@Order(30.0)
		public class ValueColumn extends AbstractStringColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Value");
			}

			@Override
			protected boolean getConfiguredEditable() {
				return true;
			}

			@Override
			protected IFormField execPrepareEdit(final ITableRow row) throws ProcessingException {
				// extract value and datatype
				String value = (String) row.getCellValue(getTable().getColumnSet().getColumnCount() - 2);
				Long datatype = (Long) row.getCellValue(getTable().getColumnSet().getColumnCount() - 1);

				// return field with or without value
				FormFieldFactory factory = new DatatypeFormFieldFactory();
				return factory.createFormField(datatype, value);

			}

			@Override
			protected void execCompleteEdit(ITableRow row, IFormField editingField) throws ProcessingException {

				@SuppressWarnings("rawtypes")
				Object value = (Object) ((AbstractValueField) editingField).getValue();

				if (value != null) {
					if (editingField instanceof AbstractDateField) {
						Date date = ((AbstractDateField) editingField).getValue();
						SimpleDateFormat formatter = new SimpleDateFormat(TEXTS.get("SimpleDateFormat"));
						getValueColumn().setValue(row, formatter.format(date));
					} else {
						getValueColumn().setValue(row, value.toString());
					}
				} else {
					getValueColumn().setValue(row, null);
				}

			}

		}

		@Order(40.0)
		public class DatatypeColumn extends AbstractSmartColumn<Long> {

			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("Datatype");
			}

			@Override
			protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
				return DatatypeCodeType.class;
			}
		}
	}
}
