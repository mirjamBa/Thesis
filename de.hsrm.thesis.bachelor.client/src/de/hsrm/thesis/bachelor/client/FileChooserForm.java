package de.hsrm.thesis.bachelor.client;

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

import de.hsrm.thesis.bachelor.client.FileChooserForm.MainBox.CancelButton;
import de.hsrm.thesis.bachelor.client.FileChooserForm.MainBox.File0Box;
import de.hsrm.thesis.bachelor.client.FileChooserForm.MainBox.File0Box.File0Field;
import de.hsrm.thesis.bachelor.client.FileChooserForm.MainBox.OkButton;
import de.hsrm.thesis.bachelor.shared.FileChooserFormData;

@FormData(value = FileChooserFormData.class, sdkCommand = SdkCommand.CREATE)
public class FileChooserForm extends AbstractForm {

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

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public File0Box getFile0Box() {
    return getFieldByClass(File0Box.class);
  }

  public File0Field getFile0Field() {
    return getFieldByClass(File0Field.class);
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
    public class File0Box extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("File0");
      }

      @Order(10.0)
      public class File0Field extends AbstractFileChooserField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("File0");
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

  public class ModifyHandler extends AbstractFormHandler {
  }

  public class NewHandler extends AbstractFormHandler {
  }
}
