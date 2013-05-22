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
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.client.MetadataForm.MainBox.BezeichnungField;
import de.hsrm.thesis.bachelor.client.MetadataForm.MainBox.CancelButton;
import de.hsrm.thesis.bachelor.client.MetadataForm.MainBox.DatatypeField;
import de.hsrm.thesis.bachelor.client.MetadataForm.MainBox.OkButton;
import de.hsrm.thesis.bachelor.shared.IMetadataService;
import de.hsrm.thesis.bachelor.shared.MetadataFormData;
import de.hsrm.thesis.bachelor.shared.UpdateMetadataPermission;

@FormData(value = MetadataFormData.class, sdkCommand = SdkCommand.CREATE)
public class MetadataForm extends AbstractForm {

  public MetadataForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Metadata");
  }

  public BezeichnungField getBezeichnungField() {
    return getFieldByClass(BezeichnungField.class);
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

  public DatatypeField getDatatypeField() {
    return getFieldByClass(DatatypeField.class);
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
    public class BezeichnungField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Bezeichnung");
      }
    }

    @Order(20.0)
    public class DatatypeField extends AbstractSmartField<Long> {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Datatype");
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

    @Override
    public void execLoad() throws ProcessingException {
      IMetadataService service = SERVICES.getService(IMetadataService.class);
      MetadataFormData formData = new MetadataFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateMetadataPermission());
    }

    @Override
    public void execStore() throws ProcessingException {
      IMetadataService service = SERVICES.getService(IMetadataService.class);
      MetadataFormData formData = new MetadataFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IMetadataService service = SERVICES.getService(IMetadataService.class);
      MetadataFormData formData = new MetadataFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      IMetadataService service = SERVICES.getService(IMetadataService.class);
      MetadataFormData formData = new MetadataFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}
