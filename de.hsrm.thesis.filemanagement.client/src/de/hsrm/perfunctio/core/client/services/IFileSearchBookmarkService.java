package de.hsrm.perfunctio.core.client.services;

import org.eclipse.scout.service.IService;
import org.eclipse.scout.commons.exception.ProcessingException;

/**
 * Service interface for handling search bookmarks
 * 
 * @author Mirjam Bayatloo
 * 
 */
public interface IFileSearchBookmarkService extends IService {

	/**
	 * Saves the current searchdata for the user
	 * 
	 * @param filename
	 *            String
	 * @param xml
	 *            String
	 * @throws ProcessingException
	 */
	public void exportFileSearch(String filename, String xml)
			throws ProcessingException;

	/**
	 * Fetches all bookmarks from the current client for the current user
	 * [bookmark-name]
	 * 
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getBookmarks() throws ProcessingException;

	/**
	 * Returns the searchdata as XML for the assigned bookmark-name
	 * 
	 * @param filename
	 *            String
	 * @return String
	 * @throws ProcessingException
	 */
	public String getBookmarkXml(String filename) throws ProcessingException;

	/**
	 * Removes the searchdata, assigned to the bookmark-name from the client.
	 * 
	 * @param filename
	 *            String
	 * @return String
	 * @throws ProcessingException
	 */
	public boolean deleteBookmark(String filename) throws ProcessingException;
}
