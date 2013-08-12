package de.hsrm.perfunctio.core.client.ui;

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;

/**
 * Abstract factory for field modeling
 * 
 * @author Mirjam Bayatloo
 * 
 */
public abstract class FormFieldFactory {

	/**
	 * Creates a new FormField
	 * 
	 * @param datatypeId
	 *            Log
	 * @param value
	 *            String
	 * @return IFormField
	 */
	public abstract IFormField createFormField(Long datatypeId, String value);

}
