package de.hsrm.thesis.filemanagement.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPage;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm;
import org.eclipse.scout.commons.annotations.FormData;

public class FilePage extends AbstractPage {
	FileForm fileForm;
	private Long m_fileId;
	
	public FilePage(FileForm form, Long fileId){
		super();
		fileForm = form;
		m_fileId = fileId;
	}

	@Override
	protected boolean getConfiguredLeaf() {
		return true;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("File");
	}
	
	@Override
	protected String getConfiguredIconId() {
		return AbstractIcons.DateFieldDate;
	}
	
	@Override
	protected void execPageActivated() throws ProcessingException {
		fileForm.setDisplayHint(IForm.DISPLAY_HINT_VIEW);
		fileForm.setDisplayViewId(IForm.VIEW_ID_PAGE_DETAIL);
		fileForm.setEnabledGranted(false);
		fileForm.startModify();
	}
	
	@Override
	protected void execPageDeactivated() throws ProcessingException {
		fileForm.doClose();
	}

	@FormData
	public Long getFileId() {
		return m_fileId;
	}

	@FormData
	public void setFileId(Long fileId) {
		m_fileId = fileId;
	}
	
	public void closeForm() throws ProcessingException{
		fileForm.doClose();
	}
	
}
