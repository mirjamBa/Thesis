package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

import de.hsrm.thesis.bachelor.shared.services.lookup.FiletypeLookupCall;

public class VersionFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public VersionFormData() {
  }

  public FileType0 getFileType0() {
    return getFieldByClass(FileType0.class);
  }

  public Version getVersion() {
    return getFieldByClass(Version.class);
  }

  public static class FileType0 extends AbstractValueFieldData<Long> {
    private static final long serialVersionUID = 1L;

    public FileType0() {
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

  public static class Version extends AbstractValueFieldData<Boolean> {
    private static final long serialVersionUID = 1L;

    public Version() {
    }
  }
}
