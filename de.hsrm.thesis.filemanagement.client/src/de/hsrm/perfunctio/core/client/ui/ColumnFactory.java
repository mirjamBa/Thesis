package de.hsrm.perfunctio.core.client.ui;

import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;

/**
 * Asbtract class for column modeling
 * 
 * @author Mirjam Bayatloo
 * 
 */
public abstract class ColumnFactory {

	/**
	 * Creates a new column
	 * 
	 * @param datatypeId
	 *            Long
	 * @param columnId
	 *            String
	 * @param label
	 *            String
	 * @return IColumn<?>
	 */
	public abstract IColumn<?> createColumn(Long datatypeId,
			final String columnId, final String label);

}
