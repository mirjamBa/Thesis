package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

public class MetadataFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public MetadataFormData() {
  }

  public Bezeichnung getBezeichnung() {
    return getFieldByClass(Bezeichnung.class);
  }

  public Datatype getDatatype() {
    return getFieldByClass(Datatype.class);
  }

  public static class Bezeichnung extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public Bezeichnung() {
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

  public static class Datatype extends AbstractValueFieldData<Long> {
    private static final long serialVersionUID = 1L;

    public Datatype() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
    }
  }
}
