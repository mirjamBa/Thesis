package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

public class TestFormData extends FileFormData {
  private static final long serialVersionUID = 1L;

  public TestFormData() {
  }

  public Testfield getTestfield() {
    return getFieldByClass(Testfield.class);
  }

  public static class Testfield extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public Testfield() {
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
