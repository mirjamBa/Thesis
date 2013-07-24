package de.hsrm.perfunctio.administration.client.ui.forms;

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
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.administration.client.ui.forms.VersionForm.MainBox.CancelButton;
import de.hsrm.perfunctio.administration.client.ui.forms.VersionForm.MainBox.OkButton;
import de.hsrm.perfunctio.administration.client.ui.forms.VersionForm.MainBox.VersionBox;
import de.hsrm.perfunctio.administration.client.ui.forms.VersionForm.MainBox.VersionBox.FileTypeField;
import de.hsrm.perfunctio.administration.client.ui.forms.VersionForm.MainBox.VersionBox.VersionField;
import de.hsrm.perfunctio.core.shared.services.IVersionService;
import de.hsrm.perfunctio.core.shared.services.code.FileTypeCodeType;
import de.hsrm.perfunctio.core.shared.services.formdata.VersionFormData;

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

  public FileTypeField getFileTypeField() {
    return getFieldByClass(FileTypeField.class);
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
      public class FileTypeField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FileType");
        }

        @Override
        protected Class<? extends ICodeType<?>> getConfiguredCodeType() {
        	return FileTypeCodeType.class;
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
    public void execStore() throws ProcessingException {
      VersionFormData formData = new VersionFormData();
      exportFormData(formData);
      formData = SERVICES.getService(IVersionService.class).updateFileTypeVersionControl(formData);
    }
  }

}
