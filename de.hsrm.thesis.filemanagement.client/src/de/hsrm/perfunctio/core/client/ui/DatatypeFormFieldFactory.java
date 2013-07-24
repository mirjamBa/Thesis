package de.hsrm.perfunctio.core.client.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.doublefield.AbstractDoubleField;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.perfunctio.core.shared.services.code.DatatypeCodeType;

public class DatatypeFormFieldFactory extends FormFieldFactory {

	@Override
	public IFormField createFormField(Long datatypeId, String value) {
		IFormField field;
		if (datatypeId.equals(DatatypeCodeType.DateCode.ID)) {
			SimpleDateFormat formatter = new SimpleDateFormat(TEXTS.get("SimpleDateFormat"));
			field = new AbstractDateField() {
			};
			if (value != null) {
				try {
					((AbstractDateField) field).setValue(formatter.parse(value));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			return field;
		} else if (datatypeId.equals(DatatypeCodeType.LongCode.ID)) {
			field = new AbstractLongField() {
			};
			if (value != null) {
				((AbstractLongField) field).setValue(Long.parseLong(value));
			}
			return field;
		} else if (datatypeId.equals(DatatypeCodeType.DoubleCode.ID)) {
			field = new AbstractDoubleField() {
			};
			if (value != null) {
				((AbstractDoubleField) field).setValue(Double.parseDouble(value));
			}
			return field;
		} else {
			field = new AbstractStringField() {
			};
			if (value != null) {
				((AbstractStringField) field).setValue(value);
			}
			return field;
		}
	}

}
