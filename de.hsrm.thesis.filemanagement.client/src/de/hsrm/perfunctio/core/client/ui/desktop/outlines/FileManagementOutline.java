package de.hsrm.perfunctio.core.client.ui.desktop.outlines;

import java.util.Collection;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;

import de.hsrm.perfunctio.core.client.ui.desktop.outlines.pages.FilesAndFoldersTablePage;
import de.hsrm.perfunctio.core.client.ui.desktop.outlines.pages.ImageTablePage;

/**
 * Outline for file management with the FilesAndFoldersTablePage and the
 * ImageTablePage
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class FileManagementOutline extends AbstractFileManagementOutline {

	@Override
	protected void execCreateChildPages(Collection<IPage> pageList)
			throws ProcessingException {
		pageList.add(new FilesAndFoldersTablePage(null));
		pageList.add(new ImageTablePage(null, 150, 3));
	}

}
