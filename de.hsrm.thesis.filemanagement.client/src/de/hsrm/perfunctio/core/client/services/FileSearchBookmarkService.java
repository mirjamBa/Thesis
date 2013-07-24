package de.hsrm.perfunctio.core.client.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.services.IUserProcessService;

public class FileSearchBookmarkService extends AbstractService
		implements
			IFileSearchBookmarkService {
	private static final String FILE_EXTENSION = ".xml";
	private String bookmarkPath;

	public String getBookmarkPath() {
		return bookmarkPath;
	}

	public void setBookmarkPath(String path) {
		bookmarkPath = path;
	}

	@Override
	public void exportFileSearch(String filename, String xml)
			throws ProcessingException {

		String username = SERVICES.getService(IUserProcessService.class)
				.getCurrentUserName();
		File dir = new File(getBookmarkPath()
				+ username + "\\");
		dir.mkdirs();

		String filePath = getBookmarkPath()
				+ username + "\\" + filename
				+ FILE_EXTENSION;
		File f = new File(filePath);
		try {
			f.createNewFile();
			IOUtility.writeContent(new FileOutputStream(f), xml.getBytes());
		} catch (IOException e1) {
			throw new ProcessingException("cannot create bookmark: "
					+ e1.getMessage());
		}

	}

	@Override
	public Object[][] getBookmarks() throws ProcessingException {
		File fir = new File(getBookmarkPath()
				+ SERVICES.getService(IUserProcessService.class).getCurrentUserName() + "\\");
		String[] filenames = fir.list();
		if (filenames != null) {
			Object[][] res = new Object[filenames.length][];

			for (int i = 0; i < filenames.length; i++) {
				res[i] = new Object[]{filenames[i].replace(FILE_EXTENSION, "")};
			}
			return res;
		} else {
			return new Object[][]{};
		}
	}

	@Override
	public String getBookmarkXml(String filename) throws ProcessingException {
		File f = new File(getBookmarkPath()
				+ SERVICES.getService(IUserProcessService.class).getCurrentUserName() + "\\" + filename
				+ FILE_EXTENSION);
		try {
			String content = IOUtility.getContent(new FileReader(f));
			return content;
		} catch (FileNotFoundException e) {
			throw new ProcessingException("cannot load bookmark: "
					+ e.getMessage());
		}
	}

	@Override
	public boolean deleteBookmark(String filename) throws ProcessingException {
		File file = new File(getBookmarkPath()
				+ SERVICES.getService(IUserProcessService.class).getCurrentUserName() + "\\" + filename
				+ FILE_EXTENSION);
		return file.delete();
	}
}
