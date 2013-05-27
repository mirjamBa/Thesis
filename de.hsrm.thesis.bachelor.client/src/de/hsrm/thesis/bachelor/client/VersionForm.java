package de.hsrm.thesis.bachelor.client;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.client.VersionForm.MainBox.CancelButton;
import de.hsrm.thesis.bachelor.client.VersionForm.MainBox.OkButton;
import de.hsrm.thesis.bachelor.client.VersionForm.MainBox.VersionBox;
import de.hsrm.thesis.bachelor.client.VersionForm.MainBox.VersionBox.FileType0Field;
import de.hsrm.thesis.bachelor.client.VersionForm.MainBox.VersionBox.VersionField;
import de.hsrm.thesis.bachelor.shared.IVersionService;
import de.hsrm.thesis.bachelor.shared.VersionFormData;
import de.hsrm.thesis.bachelor.shared.services.lookup.FiletypeLookupCall;

@FormData(value = VersionFormData.class, sdkCommand = SdkCommand.CREATE)
public class VersionForm extends AbstractForm {

  public VersionForm() throws ProcessingException {
    super();
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
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

  public VersionBox getVersionBox() {
    return getFieldByClass(VersionBox.class);
  }

  public VersionField getVersionField() {
    return getFieldByClass(VersionField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(30.0)
    public class VersionBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Version");
      }

      @Order(10.0)
      public class FileType0Field extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FileType0");
        }

        @Override
        protected Class<? extends LookupCall> getConfiguredLookupCall() {
          return FiletypeLookupCall.class;
        }

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }
      }

      @Order(20.0)
      public class VersionField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Version");
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
      IVersionService service = SERVICES.getService(IVersionService.class);
      VersionFormData formData = new VersionFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      IVersionService service = SERVICES.getService(IVersionService.class);
      VersionFormData formData = new VersionFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

}
