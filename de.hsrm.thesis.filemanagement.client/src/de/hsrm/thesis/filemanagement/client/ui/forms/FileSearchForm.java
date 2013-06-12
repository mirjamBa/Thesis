package de.hsrm.thesis.filemanagement.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.extension.client.ui.form.fields.smartfield.AbstractExtensibleSmartField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import de.hsrm.mi.administration.shared.services.lookup.TagLookupCall;
import de.hsrm.thesis.bachelor.shared.services.lookup.UserLookupCall;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.ResetButton;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.SearchButton;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.FieldBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.GeneralSearchBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.TagBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.GeneralSearchBox.GeneralSearchField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.TagBox.TagField;
import de.hsrm.thesis.filemanagement.shared.formdata.FileSearchFormData;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;

@FormData(value = FileSearchFormData.class, sdkCommand = SdkCommand.CREATE)
public class FileSearchForm extends AbstractSearchForm {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("FileSearchForm");
  }

  public FileSearchForm() throws ProcessingException {
    super();
  }

  @Override
  protected void execResetSearchFilter(SearchFilter searchFilter) throws ProcessingException {
    super.execResetSearchFilter(searchFilter);
    FileSearchFormData formData = new FileSearchFormData();
    exportFormData(formData);
    searchFilter.setFormData(formData);
  }

  @Override
  public void startSearch() throws ProcessingException {
    startInternal(new SearchHandler());
  }

  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  public GeneralSearchBox getGeneralSearchBox() {
    return getFieldByClass(GeneralSearchBox.class);
  }

  public GeneralSearchField getGeneralSearchField() {
    return getFieldByClass(GeneralSearchField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ResetButton getResetButton() {
    return getFieldByClass(ResetButton.class);
  }

  public SearchButton getSearchButton() {
    return getFieldByClass(SearchButton.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  public TagBox getTagBox() {
    return getFieldByClass(TagBox.class);
  }

  public TagField getTagField() {
    return getFieldByClass(TagField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class TabBox extends AbstractTabBox {

      @Order(10.0)
      public class GeneralSearchBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("GeneralSearch");
        }

        @Order(10.0)
        public class GeneralSearchField extends AbstractStringField {

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("GeneralSearchFieldToolTip");
          }

        }
      }

      @Order(20.0)
      public class FieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Detailsearch");
        }

        @Order(20.0)
        public class FileTypeField extends AbstractExtensibleSmartField<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("FileType");
          }

          @Override
          protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
            return FileTypeCodeType.class;
          }
        }

        @Order(10.0)
        public class FileNrBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("FileNr");
          }

          @Order(10.0)
          public class FileNrFrom extends AbstractLongField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20.0)
          public class FileNrTo extends AbstractLongField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(30.0)
        public class TypistField extends AbstractExtensibleSmartField<Long> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Typist");
          }

          @Override
          protected Class<? extends LookupCall> getConfiguredLookupCall() {
            return UserLookupCall.class;
          }
        }
      }

      @Order(30.0)
      public class TagBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Tag");
        }

        @Order(10.0)
        public class TagField extends AbstractListBox<Long> {

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Tag");
          }

          @Override
          protected Class<? extends LookupCall> getConfiguredLookupCall() {
            return TagLookupCall.class;
          }
        }
      }
    }

    @Order(20.0)
    public class ResetButton extends AbstractResetButton {
    }

    @Order(30.0)
    public class SearchButton extends AbstractSearchButton {
    }
  }

  public class SearchHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
    }
  }
}
