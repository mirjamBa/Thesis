package de.hsrm.thesis.bachelor.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.InjectFieldTo;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.thesis.bachelor.shared.TestFormData;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm;

@FormData(value = TestFormData.class, sdkCommand = SdkCommand.CREATE)
public class TestForm extends FileForm {

//  public TestForm() throws ProcessingException {
////    super();
//  }

  /**
   * @param fileNr
   * @throws ProcessingException
   */
  public TestForm(Long fileNr) throws ProcessingException {
    super(fileNr);
    // TODO Auto-generated constructor stub
  }

  @Override
  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  @Override
  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  @Order(60.0)
  @InjectFieldTo(FileForm.MainBox.File0Box.class)
  public class TestBox extends AbstractGroupBox {
    @Order(10.0)
    public class Testfield extends AbstractStringField {

      @Override
      protected boolean getConfiguredVisible() {
        return true;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Test");
      }
    }

  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execStore() throws ProcessingException {
//      IFileService service = SERVICES.getService(IFileService.class);
      TestFormData formData = new TestFormData();
      exportFormData(formData);
//      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execStore() throws ProcessingException {
//      IFileService service = SERVICES.getService(IFileService.class);
      TestFormData formData = new TestFormData();
      exportFormData(formData);
//      formData = service.create(formData);
    }
  }

}
