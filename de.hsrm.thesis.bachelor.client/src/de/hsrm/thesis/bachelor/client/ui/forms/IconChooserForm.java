package de.hsrm.thesis.bachelor.client.ui.forms;

import org.eclipse.scout.commons.IOUtility;
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

import de.hsrm.thesis.bachelor.client.ClientSession;
import de.hsrm.thesis.bachelor.client.ui.forms.IconChooserForm.MainBox.CancelButton;
import de.hsrm.thesis.bachelor.client.ui.forms.IconChooserForm.MainBox.IconField;
import de.hsrm.thesis.bachelor.client.ui.forms.IconChooserForm.MainBox.OkButton;
import de.hsrm.thesis.bachelor.shared.IIconProcessService;

public class IconChooserForm extends AbstractForm {
  public IconChooserForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("IconChangeTitle");
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public IconField getIconField() {
    return getFieldByClass(IconField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class IconField extends AbstractFileChooserField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Icon");
      }

      @Override
      protected boolean getConfiguredTypeLoad() {
        return true;
      }

      @Override
      protected boolean getConfiguredMandatory() {
        return true;
      }

    }

    @Order(20.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    protected void execStore() throws ProcessingException {
      String filename = getIconField().getValue();
      byte[] content = IOUtility.getContent(filename);
      SERVICES.getService(IIconProcessService.class).saveIcon(ClientSession.get().getUserId(), content);
    }
  }
}
