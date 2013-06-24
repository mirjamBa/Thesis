package de.hsrm.thesis.filemanagement.client.ui;

import org.eclipse.scout.rt.client.ui.form.fields.IFormField;

public abstract class FormFieldFactory {
	
	public abstract IFormField createFormField(Long datatypeId, String value);

}
