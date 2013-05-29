package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

public class FileChooserFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public FileChooserFormData() {
  }

  public File0 getFile0() {
    return getFieldByClass(File0.class);
  }

  public static class File0 extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public File0() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.MAX_LENGTH, 4000);
    }
  }
}