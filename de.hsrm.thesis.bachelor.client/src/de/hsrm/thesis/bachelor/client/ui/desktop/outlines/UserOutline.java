package de.hsrm.thesis.bachelor.client.ui.desktop.outlines;

import java.util.Collection;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages.RoleTablePage;
import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages.UserAdministrationTablePage;

public class UserOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("User");
  }

  @Override
  protected void execCreateChildPages(Collection<IPage> pageList) throws ProcessingException {
    UserAdministrationTablePage page = new UserAdministrationTablePage();
    pageList.add(page);
    pageList.add(new RoleTablePage());
  }

  @Override
  protected void execInitTree() throws ProcessingException {
//    setVisible(UserAgentUtility.isDesktopDevice() &&
//        (ACCESS.check(new CreateUserPermission()) || ACCESS.check(new DeleteUserPermission()) ||
//            ACCESS.check(new ResetPasswordPermission()) || ACCESS.check(new UpdateUserPermission())));
  }
}
