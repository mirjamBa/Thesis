package de.hsrm.perfunctio.administration.client.ui.desktop.outlines.pages;

import java.util.Collection;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;

/**
 * NodePage, used by the FileTypeTablePage for adding the MetadataTablePage and
 * FileFormatTablePage as child pages to every file type
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class FiletypeAttributesNodePage extends AbstractPageWithNodes {

	private long m_filetypeNr;

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("FiletypeAttributes");
	}

	@Override
	protected void execCreateChildPages(Collection<IPage> pageList)
			throws ProcessingException {
		MetadataTablePage metadataTablePage = new MetadataTablePage();
		metadataTablePage.setFiletypeNr(getFiletypeNr());
		pageList.add(metadataTablePage);

		FileFormatTablePage fileFormatTablePage = new FileFormatTablePage();
		fileFormatTablePage.setFiletypeNr(getFiletypeNr());
		pageList.add(fileFormatTablePage);

	}

	@FormData
	public long getFiletypeNr() {
		return m_filetypeNr;
	}

	@FormData
	public void setFiletypeNr(long filetypeNr) {
		m_filetypeNr = filetypeNr;
	}
}
