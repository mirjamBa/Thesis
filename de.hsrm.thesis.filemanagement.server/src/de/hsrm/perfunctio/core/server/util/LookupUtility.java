package de.hsrm.perfunctio.core.server.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * Utility class for looup-rows
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class LookupUtility {

	public static LookupRow[] sortLookupRows(List<LookupRow> rows) {
		Collections.sort(rows, new Comparator<LookupRow>() {
			public int compare(LookupRow firstRow, LookupRow secondRow) {
				return firstRow.getText().compareTo(secondRow.getText());
			}
		});
		return rows.toArray(new LookupRow[rows.size()]);
	}

}
