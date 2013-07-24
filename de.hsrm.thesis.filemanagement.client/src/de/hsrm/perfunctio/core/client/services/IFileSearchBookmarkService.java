package de.hsrm.perfunctio.core.client.services;

import org.eclipse.scout.service.IService;
import org.eclipse.scout.commons.exception.ProcessingException;

public interface IFileSearchBookmarkService extends IService{

	public void exportFileSearch(String filename, String xml) throws ProcessingException;

	public Object[][] getBookmarks() throws ProcessingException;

	public String getBookmarkXml(String filename) throws ProcessingException;
	
	public boolean deleteBookmark(String filename) throws ProcessingException;
}
