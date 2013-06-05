package de.hsrm.thesis.bachelor.client;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractObjectColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.client.FileForm.MainBox.CancelButton;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.AuthorityBox;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.AuthorityBox.AvailableUserField;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.AuthorityBox.ChoosenUserField;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.AuthorityBox.Remove0Button;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.DetailedBox;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.DetailedBox.AttributeField;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.MetadataBox;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.MetadataBox.AuthorField;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.MetadataBox.CreationDateField;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.MetadataBox.FileTypeField;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.MetadataBox.FilesizeField;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.MetadataBox.TitleField;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.MetadataBox.TypistField;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.RemarkBox;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.RemarkBox.RemarkField;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.TagBox;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.TagBox.AddButton;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.TagBox.AvailableTagsBox;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.TagBox.ChoosenTagsBox;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.TagBox.NewTagField;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.TagBox.RemoveButton;
import de.hsrm.thesis.bachelor.client.FileForm.MainBox.OkButton;
import de.hsrm.thesis.bachelor.shared.FileFormData;
import de.hsrm.thesis.bachelor.shared.IFileService;

@FormData(value = FileFormData.class, sdkCommand = SdkCommand.CREATE)
public class FileForm extends AbstractForm {

  private Long fileNr;

  public FileForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("File");
  }

  public AddButton getAdd0Button() {
    return getFieldByClass(AddButton.class);
  }

  public de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.AuthorityBox.AddButton getAddButton() {
    return getFieldByClass(de.hsrm.thesis.bachelor.client.FileForm.MainBox.File0Box.AuthorityBox.AddButton.class);
  }

  public AttributeField getAttributeField() {
    return getFieldByClass(AttributeField.class);
  }

  public AuthorField getAuthorField() {
    return getFieldByClass(AuthorField.class);
  }

  public AuthorityBox getAuthorityBox() {
    return getFieldByClass(AuthorityBox.class);
  }

  public AvailableTagsBox getAvailableTagsBox() {
    return getFieldByClass(AvailableTagsBox.class);
  }

  public AvailableUserField getAvailableUserField() {
    return getFieldByClass(AvailableUserField.class);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  @FormData
  public Long getFileNr() {
    return fileNr;
  }

  @FormData
  public void setFileNr(Long fileNr) {
    this.fileNr = fileNr;
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public ChoosenTagsBox getChoosenTagsBox() {
    return getFieldByClass(ChoosenTagsBox.class);
  }

  public ChoosenUserField getChoosenUserField() {
    return getFieldByClass(ChoosenUserField.class);
  }

  public CreationDateField getCreationDateField() {
    return getFieldByClass(CreationDateField.class);
  }

  public DetailedBox getDetailedBox() {
    return getFieldByClass(DetailedBox.class);
  }

  public File0Box getFile0Box() {
    return getFieldByClass(File0Box.class);
  }

  public FileTypeField getFileTypeField() {
    return getFieldByClass(FileTypeField.class);
  }

  public FilesizeField getFilesizeField() {
    return getFieldByClass(FilesizeField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MetadataBox getMetadataBox() {
    return getFieldByClass(MetadataBox.class);
  }

  public NewTagField getNewTagField() {
    return getFieldByClass(NewTagField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public RemarkBox getRemarkBox() {
    return getFieldByClass(RemarkBox.class);
  }

  public RemarkField getRemarkField() {
    return getFieldByClass(RemarkField.class);
  }

  public Remove0Button getRemove0Button() {
    return getFieldByClass(Remove0Button.class);
  }

  public RemoveButton getRemoveButton() {
    return getFieldByClass(RemoveButton.class);
  }

  public TagBox getTagBox() {
    return getFieldByClass(TagBox.class);
  }

  public TitleField getTitleField() {
    return getFieldByClass(TitleField.class);
  }

  public TypistField getTypistField() {
    return getFieldByClass(TypistField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class File0Box extends AbstractTabBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("File0");
      }

      @Order(10.0)
      public class MetadataBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Metadata");
        }

        @Order(10.0)
        public class TitleField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Title");
          }
        }

        @Order(20.0)
        public class AuthorField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Author");
          }
        }

        @Order(30.0)
        public class CreationDateField extends AbstractDateField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("CreationDate");
          }
        }

        @Order(40.0)
        public class TypistField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Typist");
          }
        }

        @Order(50.0)
        public class FileTypeField extends AbstractSmartField<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("FileType");
          }
        }

        @Order(60.0)
        public class FilesizeField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Filesize");
          }
        }
      }

      @Order(20.0)
      public class DetailedBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Detailed");
        }

        @Order(10.0)
        public class AttributeField extends AbstractTableField<AttributeField.Table> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Attribute");
          }

          @Order(10.0)
          public class Table extends AbstractExtensibleTable {

            public ValueColumn getValueColumn() {
              return getColumnSet().getColumnByClass(ValueColumn.class);
            }

            @Override
            protected boolean getConfiguredAutoResizeColumns() {
              return true;
            }

            @Override
            protected boolean getConfiguredMultiSelect() {
              return false;
            }

            public AttributeColumn getAttributeColumn() {
              return getColumnSet().getColumnByClass(AttributeColumn.class);
            }

            @Order(10.0)
            public class AttributeColumn extends AbstractStringColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Attribute");
              }
            }

            @Order(20.0)
            public class ValueColumn extends AbstractObjectColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Value");
              }

              @Override
              protected boolean getConfiguredEditable() {
                return true;
              }

            }
          }
        }

      }

      @Order(30.0)
      public class TagBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 3;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Tag");
        }

        @Order(10.0)
        public class AvailableTagsBox extends AbstractListBox {

          @Override
          protected int getConfiguredGridH() {
            return 2;
          }

          @Override
          protected int getConfiguredGridX() {
            return 0;
          }

          @Override
          protected int getConfiguredGridY() {
            return 0;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("AvailableTags");
          }

        }

        @Order(30.0)
        public class AddButton extends AbstractButton {

          //          @Override
          //          protected double getConfiguredGridWeightY() {
          //            return 10.0;
          //          }

          @Override
          protected int getConfiguredGridX() {
            return 1;
          }

          @Override
          protected int getConfiguredGridY() {
            return 0;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Add");
          }
        }

        @Order(40.0)
        public class RemoveButton extends AbstractButton {

          //          @Override
          //          protected double getConfiguredGridWeightY() {
          //            return 10.0;
          //          }

          @Override
          protected int getConfiguredGridX() {
            return 1;
          }

          @Override
          protected int getConfiguredGridY() {
            return 1;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Remove0");
          }
        }

        @Order(50.0)
        public class ChoosenTagsBox extends AbstractListBox {

          @Override
          protected int getConfiguredGridH() {
            return 2;
          }

          @Override
          protected int getConfiguredGridX() {
            return 2;
          }

          @Override
          protected int getConfiguredGridY() {
            return 0;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ChoosenTags");
          }
        }

        @Order(60.0)
        public class NewTagField extends AbstractStringField {

          @Override
          protected int getConfiguredGridW() {
            return 3;
          }

          @Override
          protected int getConfiguredGridX() {
            return 0;
          }

          @Override
          protected int getConfiguredGridY() {
            return 2;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("NewTag");
          }
        }
      }

      @Order(40.0)
      public class AuthorityBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 3;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Authority");
        }

        @Order(10.0)
        public class AvailableUserField extends AbstractListBox<Long> {

          @Override
          protected int getConfiguredGridH() {
            return 2;
          }

          @Override
          protected int getConfiguredGridX() {
            return 0;
          }

          @Override
          protected int getConfiguredGridY() {
            return 0;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("AvailableUser");
          }
        }

        @Order(30.0)
        public class AddButton extends AbstractButton {

          @Override
          protected int getConfiguredGridX() {
            return 1;
          }

          @Override
          protected int getConfiguredGridY() {
            return 0;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Add");
          }
        }

        @Order(40.0)
        public class Remove0Button extends AbstractButton {

          @Override
          protected int getConfiguredGridX() {
            return 1;
          }

          @Override
          protected int getConfiguredGridY() {
            return 1;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Remove0");
          }
        }

        @Order(50.0)
        public class ChoosenUserField extends AbstractListBox<Long> {

          @Override
          protected int getConfiguredGridH() {
            return 2;
          }

          @Override
          protected int getConfiguredGridX() {
            return 2;
          }

          @Override
          protected int getConfiguredGridY() {
            return 0;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ChoosenUser");
          }
        }
      }

      @Order(50.0)
      public class RemarkBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Remark");
        }

        @Order(10.0)
        public class RemarkField extends AbstractStringField {

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Remark");
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }

          @Override
          protected boolean getConfiguredWrapText() {
            return true;
          }
        }
      }
    }

    @Order(20.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IFileService service = SERVICES.getService(IFileService.class);
      FileFormData formData = new FileFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      IFileService service = SERVICES.getService(IFileService.class);
      FileFormData formData = new FileFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IFileService service = SERVICES.getService(IFileService.class);
      FileFormData formData = new FileFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      IFileService service = SERVICES.getService(IFileService.class);
      FileFormData formData = new FileFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}
