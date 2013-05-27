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
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.client.MetadataForm.MainBox.CancelButton;
import de.hsrm.thesis.bachelor.client.MetadataForm.MainBox.MetadataBox;
import de.hsrm.thesis.bachelor.client.MetadataForm.MainBox.MetadataBox.BezeichnungField;
import de.hsrm.thesis.bachelor.client.MetadataForm.MainBox.MetadataBox.DatatypeField;
import de.hsrm.thesis.bachelor.client.MetadataForm.MainBox.MetadataBox.FileType0Field;
import de.hsrm.thesis.bachelor.client.MetadataForm.MainBox.OkButton;
import de.hsrm.thesis.bachelor.shared.IMetadataService;
import de.hsrm.thesis.bachelor.shared.MetadataFormData;
import de.hsrm.thesis.bachelor.shared.services.code.DatatypeCodeType;
import de.hsrm.thesis.bachelor.shared.services.lookup.FiletypeLookupCall;

@FormData(value = MetadataFormData.class, sdkCommand = SdkCommand.CREATE)
public class MetadataForm extends AbstractForm {

  public MetadataForm() throws ProcessingException {
    super();
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

  public FileType0Field getFileType0Field() {
    return getFieldByClass(FileType0Field.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MetadataBox getMetadataBox() {
    return getFieldByClass(MetadataBox.class);
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
    public class MetadataBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Metadata");
      }

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

        @Override
        protected Class<? extends ICodeType<?>> getConfiguredCodeType() {
          return DatatypeCodeType.class;
        }
      }

      @Order(30.0)
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
      IMetadataService service = SERVICES.getService(IMetadataService.class);
      MetadataFormData formData = new MetadataFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
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
