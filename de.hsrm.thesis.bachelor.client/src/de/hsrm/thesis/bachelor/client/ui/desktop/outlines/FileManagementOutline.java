package de.hsrm.thesis.bachelor.client.ui.desktop.outlines;

import java.util.Collection;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.AbstractExtensibleOutline;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages.FileTablePage;
import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages.LogoTableTablePage;

public class FileManagementOutline extends AbstractExtensibleOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("FileManagement");
  }

  @Override
  protected void execCreateChildPages(Collection<IPage> pageList) throws ProcessingException {
    FileTablePage fileTablePage = new FileTablePage();
    pageList.add(fileTablePage);
    pageList.add(new LogoTableTablePage());

  }
}
