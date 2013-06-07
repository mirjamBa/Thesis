package de.hsrm.thesis.bachelor.client;

import java.io.File;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.client.FileChooserForm.MainBox.CancelButton;
import de.hsrm.thesis.bachelor.client.FileChooserForm.MainBox.FileBox;
import de.hsrm.thesis.bachelor.client.FileChooserForm.MainBox.FileBox.FileField;
import de.hsrm.thesis.bachelor.client.FileChooserForm.MainBox.OkButton;
import de.hsrm.thesis.bachelor.shared.FileChooserFormData;
import de.hsrm.thesis.bachelor.shared.IFileService;
import de.hsrm.thesis.bachelor.shared.files.ServerFileData;

@FormData(value = FileChooserFormData.class, sdkCommand = SdkCommand.CREATE)
public class FileChooserForm extends AbstractForm {

  private ServerFileData m_fileData;

  public FileChooserForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("FileChooser");
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public FileBox getFileBox() {
    return getFieldByClass(FileBox.class);
  }

  public FileField getFileField() {
    return getFieldByClass(FileField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(20.0)
    public class FileBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("File");
      }

      @Order(10.0)
      public class FileField extends AbstractFileChooserField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("File");
        }

        @Override
        protected boolean getConfiguredTypeLoad() {
          return true;
        }

      }
    }

    @Order(30.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(40.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execStore() throws ProcessingException {
      File file = getFileField().getValueAsFile();
      ServerFileData fileDate = SERVICES.getService(IFileService.class).saveFile(file);
      setFileData(fileDate);

//      SERVICES.getService(ITikaService.class).extractDataFromFile(file);

    }
  }

  @FormData
  public ServerFileData getFileData() {
    return m_fileData;
  }

  @FormData
  public void setFileData(ServerFileData fileData) {
    m_fileData = fileData;
  }
}
