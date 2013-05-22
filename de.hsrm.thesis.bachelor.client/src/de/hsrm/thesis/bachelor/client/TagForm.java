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
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.client.TagForm.MainBox.CancelButton;
import de.hsrm.thesis.bachelor.client.TagForm.MainBox.OkButton;
import de.hsrm.thesis.bachelor.client.TagForm.MainBox.TagNameField;
import de.hsrm.thesis.bachelor.shared.ITagService;
import de.hsrm.thesis.bachelor.shared.TagFormData;
import de.hsrm.thesis.bachelor.shared.UpdateTagPermission;

@FormData(value = TagFormData.class, sdkCommand = SdkCommand.CREATE)
public class TagForm extends AbstractForm {

  public TagForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Tag");
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

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public TagNameField getTagNameField() {
    return getFieldByClass(TagNameField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class TagNameField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("TagName");
      }
    }

    @Order(20.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      ITagService service = SERVICES.getService(ITagService.class);
      TagFormData formData = new TagFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateTagPermission());
    }

    @Override
    public void execStore() throws ProcessingException {
      ITagService service = SERVICES.getService(ITagService.class);
      TagFormData formData = new TagFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      ITagService service = SERVICES.getService(ITagService.class);
      TagFormData formData = new TagFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      ITagService service = SERVICES.getService(ITagService.class);
      TagFormData formData = new TagFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}
