package de.hsrm.mi.administration.client.ui.desktop.outlines;

import java.util.Collection;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.AbstractExtensibleOutline;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;

import de.hsrm.mi.administration.client.ui.desktop.outlines.pages.FileTypeTablePage;
import de.hsrm.mi.administration.client.ui.desktop.outlines.pages.ShowInFileTableTablePage;
import de.hsrm.mi.administration.client.ui.desktop.outlines.pages.TagTablePage;
import de.hsrm.thesis.filemanagement.shared.security.CreateUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.DeleteUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.ResetPasswordPermission;
import de.hsrm.thesis.filemanagement.shared.security.UpdateUserPermission;

public class AdministrationOutline extends AbstractExtensibleOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Administration");
  }
  
  @Override
	protected void execCreateChildPages(Collection<IPage> pageList)
			throws ProcessingException {
		pageList.add(new FileTypeTablePage());
		pageList.add(new TagTablePage());
pageList.add(new ShowInFileTableTablePage());
	}

  @Override
  protected void execInitTree() throws ProcessingException {
    setVisible(UserAgentUtility.isDesktopDevice() &&
        (ACCESS.check(new CreateUserPermission()) || ACCESS.check(new DeleteUserPermission()) ||
            ACCESS.check(new ResetPasswordPermission()) || ACCESS.check(new UpdateUserPermission())));
  }
}
