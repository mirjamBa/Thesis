package de.hsrm.thesis.bachelor.client;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import de.hsrm.thesis.bachelor.client.FiletypeChooserForm.MainBox.FiletypeChooserFormBox;
import de.hsrm.thesis.bachelor.client.FiletypeChooserForm.MainBox.FiletypeChooserFormBox.FileTypeField;
import de.hsrm.thesis.bachelor.client.FiletypeChooserForm.MainBox.FiletypeChooserFormBox.FiletypeChooserFormField;
import de.hsrm.thesis.bachelor.client.FiletypeChooserForm.MainBox.OkButton;
import de.hsrm.thesis.bachelor.shared.FiletypeChooserFormData;
import de.hsrm.thesis.bachelor.shared.services.lookup.FiletypeForFileformatsLookupCall;

@FormData(value = FiletypeChooserFormData.class, sdkCommand = SdkCommand.CREATE)
public class FiletypeChooserForm extends AbstractForm {

  private String m_fileformat;
  private long m_filetypeNr;

  public FiletypeChooserForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("FiletypeChooserForm");
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public FileTypeField getFileTypeField() {
    return getFieldByClass(FileTypeField.class);
  }

  public FiletypeChooserFormBox getFiletypeChooserFormBox() {
    return getFieldByClass(FiletypeChooserFormBox.class);
  }

  public FiletypeChooserFormField getFiletypeChooserFormField() {
    return getFieldByClass(FiletypeChooserFormField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10.0)
    public class FiletypeChooserFormBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("FiletypeChooserForm");
      }

      @Order(10.0)
      public class FiletypeChooserFormField extends AbstractLabelField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FiletypeChooserFormLabel");
        }
      }

      @Order(20.0)
      public class FileTypeField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FileType");
        }

        @Override
        protected void execPrepareLookup(LookupCall call) throws ProcessingException {
          FiletypeForFileformatsLookupCall lookupCall = (FiletypeForFileformatsLookupCall) call;
          lookupCall.setFileformat(getFileformat());
        }

        @Override
        protected Class<? extends LookupCall> getConfiguredLookupCall() {
          return FiletypeForFileformatsLookupCall.class;
        }
      }
    }

    @Order(20.0)
    public class OkButton extends AbstractOkButton {
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execStore() throws ProcessingException {
      FiletypeChooserFormData formData = new FiletypeChooserFormData();
      exportFormData(formData);
      setFiletypeNr(formData.getFileType().getValue());
    }
  }

  @FormData
  public String getFileformat() {
    return m_fileformat;
  }

  @FormData
  public void setFileformat(String fileformat) {
    m_fileformat = fileformat;
  }

  @FormData
  public long getFiletypeNr() {
    return m_filetypeNr;
  }

  @FormData
  public void setFiletypeNr(long filetypeNr) {
    m_filetypeNr = filetypeNr;
  }
}
