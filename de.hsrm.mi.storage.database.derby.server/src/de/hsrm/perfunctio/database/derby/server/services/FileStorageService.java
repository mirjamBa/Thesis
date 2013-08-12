package de.hsrm.perfunctio.database.derby.server.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.perfunctio.core.shared.beans.ServerFileData;
import de.hsrm.perfunctio.core.shared.security.CreateFilePermission;
import de.hsrm.perfunctio.core.shared.services.IFileStorageService;

public class FileStorageService extends AbstractService
		implements
			IFileStorageService {
	private SimpleDateFormat m_formatter = new SimpleDateFormat(
			TEXTS.get("SimpleDateFormat"));
	private String filePath;

	public void setFilePath(String path) {
		this.filePath = path;
	}

	public String getFilePath() {
		return filePath;
	}

	@Override
	public ServerFileData getServerFileData(File file)
			throws ProcessingException {
		if (!ACCESS.check(new CreateFilePermission())) {
			throw new VetoException(TEXTS.get("VETOCreateFilePermission"));
		}

		String fileEnding = file.getName().split("\\.")[file.getName().split(
				"\\.").length - 1];
		String currentDate = generateCurrentDate(TEXTS.get("StorageDirectory"));
		String[] parts = currentDate.split(":");

		String fileServerPath;
		Long nextFileNr = getNextFileNumber();
		String newDirectory = getFilePath() + parts[0];

		fileServerPath = newDirectory + "/" + parts[1] + "_" + nextFileNr + "."
				+ fileEnding;

		ServerFileData fileData = new ServerFileData(fileServerPath,
				nextFileNr, file.length());
		fileData.setOldName(file.getName().split("\\.")[0]);
		fileData.setFile(file);
		try {
			fileData.setLastModified(m_formatter.parse(m_formatter.format(file
					.lastModified())));
		} catch (ParseException e) {
			// no date set
		}

		return fileData;
	}

	@Override
	public void saveFile(ServerFileData fileData) throws ProcessingException {
		if (!ACCESS.check(new CreateFilePermission())) {
			throw new VetoException(TEXTS.get("VETOCreateFilePermission"));
		}

		byte[] content;
		File serverFile;

		try {
			String directory = fileData.getPath().substring(0,
					getFilePath().length() + 11);

			File dir = new File(directory);
			dir.mkdirs();

			// create new file on server
			serverFile = new File(fileData.getPath());
			serverFile.createNewFile();

			// write content to new file
			content = IOUtility.getContent(new FileInputStream(fileData
					.getFile()));
			IOUtility.writeContent(new FileOutputStream(serverFile), content,
					true);

		} catch (FileNotFoundException e1) {
			throw new ProcessingException(e1.getMessage());
		} catch (IOException e) {
			throw new ProcessingException(e.getMessage());
		}
	}

	private String generateCurrentDate(String format) {
		Date now = new Date();
		String datePath = new SimpleDateFormat(format).format(now);
		return datePath;
	}

	private Long getNextFileNumber() throws ProcessingException {
		Object[][] seqNr = SQL.select("VALUES NEXT VALUE FOR file_number");
		Long fileNumber = (Long) seqNr[0][0];
		return fileNumber;
	}

	@Override
	public boolean deleteServerFile(String path) {
		// remove file from server
		File file = new File(path);
		return file.delete();
	}

}
