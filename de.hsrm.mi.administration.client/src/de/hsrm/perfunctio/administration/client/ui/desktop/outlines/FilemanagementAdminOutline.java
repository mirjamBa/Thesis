package de.hsrm.perfunctio.administration.client.ui.desktop.outlines;

import java.util.Collection;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.AbstractExtensibleOutline;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.security.BasicHierarchyPermission;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import de.hsrm.perfunctio.administration.client.ui.desktop.outlines.pages.FileTypeTablePage;
import de.hsrm.perfunctio.administration.client.ui.desktop.outlines.pages.ShowInFileTableTablePage;
import de.hsrm.perfunctio.administration.client.ui.desktop.outlines.pages.TagTablePage;
import de.hsrm.perfunctio.administration.shared.security.ViewAdminOutlinePermission;

public class FilemanagementAdminOutline extends AbstractExtensibleOutline {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("FilemanagementAdministrationOutline");
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
		setVisibleGranted(ACCESS.getLevel(new ViewAdminOutlinePermission()) > BasicHierarchyPermission.LEVEL_NONE);
	}
}
