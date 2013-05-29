package de.hsrm.thesis.bachelor.client.ui.desktop.outlines;

import java.util.Collection;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages.FileTablePage;

public class TextFileManagementOutline extends FileManagementOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("TextFileManagement");
  }

  @Override
  protected void execCreateChildPages(Collection<IPage> pageList) throws ProcessingException {
    FileTablePage fileTablePage = new FileTablePage();
    pageList.add(fileTablePage);

  }
}