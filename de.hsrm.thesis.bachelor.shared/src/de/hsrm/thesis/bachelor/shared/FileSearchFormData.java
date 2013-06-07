package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

import de.hsrm.thesis.bachelor.shared.services.lookup.FiletypeLookupCall;
import de.hsrm.thesis.bachelor.shared.services.lookup.UserLookupCall;

public class FileSearchFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public FileSearchFormData() {
  }

  public FileNrFrom getFileNrFrom() {
    return getFieldByClass(FileNrFrom.class);
  }

  public FileNrTo getFileNrTo() {
    return getFieldByClass(FileNrTo.class);
  }

  public FileType getFileType() {
    return getFieldByClass(FileType.class);
  }

  public Typist getTypist() {
    return getFieldByClass(Typist.class);
  }

  public static class FileNrFrom extends AbstractValueFieldData<Long> {
    private static final long serialVersionUID = 1L;

    public FileNrFrom() {
    }
  }

  public static class FileNrTo extends AbstractValueFieldData<Long> {
    private static final long serialVersionUID = 1L;

    public FileNrTo() {
    }
  }

  public static class FileType extends AbstractValueFieldData<Long> {
    private static final long serialVersionUID = 1L;

    public FileType() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.LOOKUP_CALL, FiletypeLookupCall.class);
      ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
    }
  }

  public static class Typist extends AbstractValueFieldData<Long> {
    private static final long serialVersionUID = 1L;

    public Typist() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.LOOKUP_CALL, UserLookupCall.class);
      ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
    }
  }
}
