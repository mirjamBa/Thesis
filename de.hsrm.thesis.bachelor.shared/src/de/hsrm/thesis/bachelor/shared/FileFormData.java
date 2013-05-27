package de.hsrm.thesis.bachelor.shared;

import java.util.Date;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

public class FileFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public FileFormData() {
  }

  public FileNrProperty getFileNrProperty() {
    return getPropertyByClass(FileNrProperty.class);
  }

  /**
   * access method for property FileNr.
   */
  public Long getFileNr() {
    return getFileNrProperty().getValue();
  }

  /**
   * access method for property FileNr.
   */
  public void setFileNr(Long fileNr) {
    getFileNrProperty().setValue(fileNr);
  }

  public Attribute getAttribute() {
    return getFieldByClass(Attribute.class);
  }

  public Author getAuthor() {
    return getFieldByClass(Author.class);
  }

  public AvailableTagsBox getAvailableTagsBox() {
    return getFieldByClass(AvailableTagsBox.class);
  }

  public AvailableUser getAvailableUser() {
    return getFieldByClass(AvailableUser.class);
  }

  public ChoosenTagsBox getChoosenTagsBox() {
    return getFieldByClass(ChoosenTagsBox.class);
  }

  public ChoosenUser getChoosenUser() {
    return getFieldByClass(ChoosenUser.class);
  }

  public CreationDate getCreationDate() {
    return getFieldByClass(CreationDate.class);
  }

  public FileType0 getFileType0() {
    return getFieldByClass(FileType0.class);
  }

  public Filesize getFilesize() {
    return getFieldByClass(Filesize.class);
  }

  public NewTag getNewTag() {
    return getFieldByClass(NewTag.class);
  }

  public Remark getRemark() {
    return getFieldByClass(Remark.class);
  }

  public Title getTitle() {
    return getFieldByClass(Title.class);
  }

  public Typist getTypist() {
    return getFieldByClass(Typist.class);
  }

  public class FileNrProperty extends AbstractPropertyData<Long> {
    private static final long serialVersionUID = 1L;

    public FileNrProperty() {
    }
  }

  public static class Attribute extends AbstractTableFieldData {
    private static final long serialVersionUID = 1L;

    public Attribute() {
    }

    public static final int ATTRIBUTE_COLUMN_ID = 0;
    public static final int VALUE_COLUMN_ID = 1;

    public void setAttribute(int row, String attribute) {
      setValueInternal(row, ATTRIBUTE_COLUMN_ID, attribute);
    }

    public String getAttribute(int row) {
      return (String) getValueInternal(row, ATTRIBUTE_COLUMN_ID);
    }

    public void setValue(int row, Object value) {
      setValueInternal(row, VALUE_COLUMN_ID, value);
    }

    public Object getValue(int row) {
      return getValueInternal(row, VALUE_COLUMN_ID);
    }

    @Override
    public int getColumnCount() {
      return 2;
    }

    @Override
    public Object getValueAt(int row, int column) {
      switch (column) {
        case ATTRIBUTE_COLUMN_ID:
          return getAttribute(row);
        case VALUE_COLUMN_ID:
          return getValue(row);
        default:
          return null;
      }
    }

    @Override
    public void setValueAt(int row, int column, Object value) {
      switch (column) {
        case ATTRIBUTE_COLUMN_ID:
          setAttribute(row, (String) value);
          break;
        case VALUE_COLUMN_ID:
          setValue(row, value);
          break;
      }
    }
  }

  public static class Author extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public Author() {
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

  public static class AvailableTagsBox extends AbstractValueFieldData<Object[]> {
    private static final long serialVersionUID = 1L;

    public AvailableTagsBox() {
    }
  }

  public static class AvailableUser extends AbstractValueFieldData<Long[]> {
    private static final long serialVersionUID = 1L;

    public AvailableUser() {
    }
  }

  public static class ChoosenTagsBox extends AbstractValueFieldData<Object[]> {
    private static final long serialVersionUID = 1L;

    public ChoosenTagsBox() {
    }
  }

  public static class ChoosenUser extends AbstractValueFieldData<Long[]> {
    private static final long serialVersionUID = 1L;

    public ChoosenUser() {
    }
  }

  public static class CreationDate extends AbstractValueFieldData<Date> {
    private static final long serialVersionUID = 1L;

    public CreationDate() {
    }
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
      ruleMap.put(ValidationRule.ZERO_NULL_EQUALITY, true);
    }
  }

  public static class Filesize extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public Filesize() {
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

  public static class NewTag extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public NewTag() {
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

  public static class Remark extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public Remark() {
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

  public static class Title extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public Title() {
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

  public static class Typist extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public Typist() {
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
