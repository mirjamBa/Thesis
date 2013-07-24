package de.hsrm.perfunctio.core.server.services.lookup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import de.hsrm.perfunctio.core.server.util.LookupUtility;
import de.hsrm.perfunctio.core.shared.services.lookup.ILanguageLookupService;

/**
 * LookupService for languages corresponding to ISO 639-3
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class LanguageLookupService extends AbstractLookupService
		implements
			ILanguageLookupService {

	@Override
	public LookupRow[] getDataByAll(LookupCall call) throws ProcessingException {
		String[] locals = Locale.getISOLanguages();
		List<LookupRow> rows = new ArrayList<LookupRow>();

		for (int i = 0; i < locals.length; i++) {
			Locale l = new Locale(locals[i]);
			rows.add(new LookupRow(l.getISO3Language(), l.getDisplayName()));
		}

		return LookupUtility.sortLookupRows(rows);
	}

	@Override
	public LookupRow[] getDataByKey(LookupCall call) throws ProcessingException {
		LookupRow filteredRow = null;

		if (call.getKey() != null) {
			for (LookupRow lookupRow : getDataByAll(call)) {
				if (lookupRow.getKey().equals(call.getKey())) {
					filteredRow = lookupRow;
					break;
				}
			}
		}

		return new LookupRow[]{filteredRow};
	}

	@Override
	public LookupRow[] getDataByRec(LookupCall call) throws ProcessingException {
		return getDataByAll(call);
	}

	@Override
	public LookupRow[] getDataByText(LookupCall call)
			throws ProcessingException {
		List<LookupRow> filteredRows = new LinkedList<LookupRow>();

		if (call.getText() != null) {
			String text = StringUtility.replace(call.getText().toLowerCase(),
					"*", ".*");
			try {
				Pattern langPattern = Pattern.compile(text);

				for (LookupRow lookupRow : getDataByAll(call)) {
					Matcher matcher = langPattern.matcher(lookupRow.getText()
							.toLowerCase());
					if (matcher.matches()) {
						filteredRows.add(lookupRow);
					}
				}
			} catch (PatternSyntaxException e) {
			}
		}

		return LookupUtility.sortLookupRows(filteredRows);
	}
}
