package de.hsrm.perfunctio.core.client.ui;

import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;

public abstract class ColumnFactory {
	
	public abstract IColumn<?> createColumn(Long datatypeId, final String columnId, final String label);
	

}
