package de.hsrm.thesis.bachelor.client;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.client.FileFormat0Form.MainBox.CancelButton;
import de.hsrm.thesis.bachelor.client.FileFormat0Form.MainBox.FileFormat0Box;
import de.hsrm.thesis.bachelor.client.FileFormat0Form.MainBox.FileFormat0Box.FileFormatField;
import de.hsrm.thesis.bachelor.client.FileFormat0Form.MainBox.FileFormat0Box.FileType0Field;
import de.hsrm.thesis.bachelor.client.FileFormat0Form.MainBox.OkButton;
import de.hsrm.thesis.bachelor.shared.FileFormat0FormData;
import de.hsrm.thesis.bachelor.shared.IFileFormat0Service;
import de.hsrm.thesis.bachelor.shared.services.lookup.FiletypeLookupCall;

@FormData(value = FileFormat0FormData.class, sdkCommand = SdkCommand.CREATE)
public class FileFormat0Form extends AbstractForm {

  public FileFormat0Form() throws ProcessingException {
    super();
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public FileFormat0Box getFileFormat0Box() {
    return getFieldByClass(FileFormat0Box.class);
  }

  public FileFormatField getFileFormatField() {
    return getFieldByClass(FileFormatField.class);
  }

  public FileType0Field getFileType0Field() {
    return getFieldByClass(FileType0Field.class);
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

    @Order(30.0)
    public class FileFormat0Box extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("FileFormat0");
      }

      @Order(10.0)
      public class FileFormatField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FileFormat");
        }
      }

      @Order(20.0)
      public class FileType0Field extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FileType0");
        }

        @Override
        protected Class<? extends LookupCall> getConfiguredLookupCall() {
          return FiletypeLookupCall.class;
        }
      }
    }

    @Order(40.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(50.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IFileFormat0Service service = SERVICES.getService(IFileFormat0Service.class);
      FileFormat0FormData formData = new FileFormat0FormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      IFileFormat0Service service = SERVICES.getService(IFileFormat0Service.class);
      FileFormat0FormData formData = new FileFormat0FormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IFileFormat0Service service = SERVICES.getService(IFileFormat0Service.class);
      FileFormat0FormData formData = new FileFormat0FormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      IFileFormat0Service service = SERVICES.getService(IFileFormat0Service.class);
      FileFormat0FormData formData = new FileFormat0FormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}
