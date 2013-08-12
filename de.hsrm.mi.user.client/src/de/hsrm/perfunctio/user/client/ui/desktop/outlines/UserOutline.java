package de.hsrm.perfunctio.user.client.ui.desktop.outlines;

import java.util.Collection;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.security.BasicHierarchyPermission;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import de.hsrm.perfunctio.user.client.ui.desktop.outlines.pages.PermissionTablePage;
import de.hsrm.perfunctio.user.client.ui.desktop.outlines.pages.RoleTablePage;
import de.hsrm.perfunctio.user.client.ui.desktop.outlines.pages.UserAdministrationTablePage;
import de.hsrm.perfunctio.user.shared.security.ViewUserOutlinePermission;

/**
 * Outline with the UserAdministrationTablePage, the RoleTablePage and the
 * PermissionTablePage
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class UserOutline extends AbstractOutline {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("User");
	}

	@Override
	protected void execCreateChildPages(Collection<IPage> pageList)
			throws ProcessingException {
		UserAdministrationTablePage page = new UserAdministrationTablePage();
		pageList.add(page);
		pageList.add(new RoleTablePage());
		pageList.add(new PermissionTablePage());
	}

	@Override
	protected void execInitTree() throws ProcessingException {
		setVisibleGranted(ACCESS.getLevel(new ViewUserOutlinePermission()) > BasicHierarchyPermission.LEVEL_NONE);
	}
}
