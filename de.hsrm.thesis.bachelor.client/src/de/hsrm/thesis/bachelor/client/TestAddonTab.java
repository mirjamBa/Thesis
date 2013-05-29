/**
 * 
 */
package de.hsrm.thesis.bachelor.client;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;

public class TestAddonTab extends AbstractGroupBox {

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("MeineExtendedBox");
  }

  @Order(10.0)
  public class TitleField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Title");
    }
  }

  @Order(20.0)
  public class AuthorField extends AbstractStringField {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Author");
    }
  }

}
