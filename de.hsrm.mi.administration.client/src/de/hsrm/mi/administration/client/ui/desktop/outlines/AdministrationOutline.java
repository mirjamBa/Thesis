package de.hsrm.mi.administration.client.ui.desktop.outlines;

import java.util.Collection;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.mi.administration.client.ui.desktop.outlines.pages.FileTypeTablePage;
import de.hsrm.mi.administration.client.ui.desktop.outlines.pages.TagTablePage;

public class AdministrationOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Administration");
  }
  
  @Override
	protected void execCreateChildPages(Collection<IPage> pageList)
			throws ProcessingException {
		pageList.add(new FileTypeTablePage());
		pageList.add(new TagTablePage());
	}

//  @Override
//  protected void execInitTree() throws ProcessingException {
//    setVisible(UserAgentUtility.isDesktopDevice() &&
//        (ACCESS.check(new CreateUserPermission()) || ACCESS.check(new DeleteUserPermission()) ||
//            ACCESS.check(new ResetPasswordPermission()) || ACCESS.check(new UpdateUserPermission())));
//  }
}
