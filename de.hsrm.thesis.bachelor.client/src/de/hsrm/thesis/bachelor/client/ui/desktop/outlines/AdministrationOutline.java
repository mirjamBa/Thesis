package de.hsrm.thesis.bachelor.client.ui.desktop.outlines;

import java.util.Collection;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;

import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages.FileFormatTablePage;
import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages.FileTypeTablePage;
import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages.MetadataTablePage;
import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages.TagTablePage;
import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages.VersionTablePage;
import de.hsrm.thesis.bachelor.shared.security.CreateUserPermission;
import de.hsrm.thesis.bachelor.shared.security.DeleteUserPermission;
import de.hsrm.thesis.bachelor.shared.security.ResetPasswordPermission;
import de.hsrm.thesis.bachelor.shared.security.UpdateUserPermission;

public class AdministrationOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Administration");
  }

  @Override
  protected void execCreateChildPages(Collection<IPage> pageList) throws ProcessingException {
    pageList.add(new FileFormatTablePage());
    pageList.add(new TagTablePage());
    pageList.add(new MetadataTablePage());
    pageList.add(new VersionTablePage());
    pageList.add(new FileTypeTablePage());

  }

  @Override
  protected void execInitTree() throws ProcessingException {
    setVisible(UserAgentUtility.isDesktopDevice() &&
        (ACCESS.check(new CreateUserPermission()) || ACCESS.check(new DeleteUserPermission()) ||
            ACCESS.check(new ResetPasswordPermission()) || ACCESS.check(new UpdateUserPermission())));
  }
}
