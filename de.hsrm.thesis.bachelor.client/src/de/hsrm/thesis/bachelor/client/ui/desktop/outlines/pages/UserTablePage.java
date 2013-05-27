package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.thesis.bachelor.shared.security.DeleteUserPermission;

public class UserTablePage extends UserAdministrationTablePage {

  @Order(10.0)
  public class MyTable extends Table {

    @Order(60.0)
    public class MyMenu extends AbstractMenu {

      @Override
      protected boolean getConfiguredMultiSelectionAction() {
        return true;
      }

      @Override
      protected void execPrepareAction() throws ProcessingException {
        setVisiblePermission(new DeleteUserPermission());
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("DeleteMyUser");
      }

      @Override
      protected void execAction() throws ProcessingException {
        System.out.println("Menu klicked");
      }
    }

  }
}
