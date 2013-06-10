package de.hsrm.thesis.bachelor.shared;

import java.util.Date;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

import de.hsrm.mi.administration.shared.services.lookup.FiletypeLookupCall;
import de.hsrm.mi.administration.shared.services.lookup.TagLookupCall;
import de.hsrm.thesis.bachelor.shared.files.ServerFileData;
import de.hsrm.thesis.bachelor.shared.services.lookup.RoleLookupCall;
import de.hsrm.thesis.bachelor.shared.services.lookup.UserLookupCall;

public class FileFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public FileFormData() {
  }

  public FileDataProperty getFileDataProperty() {
    return getPropertyByClass(FileDataProperty.class);
  }

  /**
   * access method for property FileData.
   */
  public ServerFileData getFileData() {
    return getFileDataProperty().getValue();
  }

  /**
   * access method for property FileData.
   */
  public void setFileData(ServerFileData fileData) {
    getFileDataProperty().setValue(fileData);
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

  public FiletypeNrProperty getFiletypeNrProperty() {
    return getPropertyByClass(FiletypeNrProperty.class);
  }

  /**
   * access method for property FiletypeNr.
   */
  public long getFiletypeNr() {
    return (getFiletypeNrProperty().getValue() == null) ? (0L) : (getFiletypeNrProperty().getValue());
  }

  /**
   * access method for property FiletypeNr.
   */
  public void setFiletypeNr(long filetypeNr) {
    getFiletypeNrProperty().setValue(filetypeNr);
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

  public CreationDate getCreationDate() {
    return getFieldByClass(CreationDate.class);
  }

  public FileType getFileType() {
    return getFieldByClass(FileType.class);
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

  public Roles getRoles() {
    return getFieldByClass(Roles.class);
  }

  public Title getTitle() {
    return getFieldByClass(Title.class);
  }

  public Typist getTypist() {
    return getFieldByClass(Typist.class);
  }

  public class FileDataProperty extends AbstractPropertyData<ServerFileData> {
    private static final long serialVersionUID = 1L;

    public FileDataProperty() {
    }
  }

  public class FileNrProperty extends AbstractPropertyData<Long> {
    private static final long serialVersionUID = 1L;

    public FileNrProperty() {
    }
  }

  public class FiletypeNrProperty extends AbstractPropertyData<Long> {
    private static final long serialVersionUID = 1L;

    public FiletypeNrProperty() {
    }
  }

  public static class Attribute extends AbstractTableFieldData {
    private static final long serialVersionUID = 1L;

    public Attribute() {
    }

    public static final int ATTRIBUTE_COLUMN_ID = 0;
    public static final int VALUE_COLUMN_ID = 1;
    public static final int DATATYPE_COLUMN_ID = 2;

    public void setAttribute(int row, String attribute) {
      setValueInternal(row, ATTRIBUTE_COLUMN_ID, attribute);
    }

    public String getAttribute(int row) {
      return (String) getValueInternal(row, ATTRIBUTE_COLUMN_ID);
    }

    public void setValue(int row, String value) {
      setValueInternal(row, VALUE_COLUMN_ID, value);
    }

    public String getValue(int row) {
      return (String) getValueInternal(row, VALUE_COLUMN_ID);
    }

    public void setDatatype(int row, Long datatype) {
      setValueInternal(row, DATATYPE_COLUMN_ID, datatype);
    }

    public Long getDatatype(int row) {
      return (Long) getValueInternal(row, DATATYPE_COLUMN_ID);
    }

    @Override
    public int getColumnCount() {
      return 3;
    }

    @Override
    public Object getValueAt(int row, int column) {
      switch (column) {
        case ATTRIBUTE_COLUMN_ID:
          return getAttribute(row);
        case VALUE_COLUMN_ID:
          return getValue(row);
        case DATATYPE_COLUMN_ID:
          return getDatatype(row);
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
          setValue(row, (String) value);
          break;
        case DATATYPE_COLUMN_ID:
          setDatatype(row, (Long) value);
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

  public static class AvailableTagsBox extends AbstractValueFieldData<Long[]> {
    private static final long serialVersionUID = 1L;

    public AvailableTagsBox() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.LOOKUP_CALL, TagLookupCall.class);
    }
  }

  public static class CreationDate extends AbstractValueFieldData<Date> {
    private static final long serialVersionUID = 1L;

    public CreationDate() {
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

  public static class Roles extends AbstractValueFieldData<Long[]> {
    private static final long serialVersionUID = 1L;

    public Roles() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.LOOKUP_CALL, RoleLookupCall.class);
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
