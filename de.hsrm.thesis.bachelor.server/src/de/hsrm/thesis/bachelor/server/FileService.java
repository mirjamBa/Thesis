package de.hsrm.thesis.bachelor.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.osgi.framework.FrameworkUtil;

import de.hsrm.thesis.bachelor.shared.FileFormData;
import de.hsrm.thesis.bachelor.shared.IFileService;

public class FileService extends AbstractService implements IFileService {

  @Override
  public FileFormData prepareCreate(FileFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public FileFormData create(FileFormData formData) throws ProcessingException {
    //TODO [mba] business logic here.
    return formData;
  }

  @Override
  public FileFormData load(FileFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public FileFormData store(FileFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public Object[][] getFiles() throws ProcessingException {
    SQL.select("SELECT file_id,"
        + "            filetype_id,"
        + "            number,"
        + "            XX Title,"
        + "            XX Author,"
        + "            u_id,"
        + "            XX Filesize,"
        + "            XX Format,"
        + "            XX Mime");

    return null;
  }

  @Override
  public String saveFile(File file) throws ProcessingException {
    URL filePath = FrameworkUtil.getBundle(getClass()).getResource("files/");
    byte[] content;
    File serverFile;

    String fileEnding = file.getName().split("\\.")[file.getName().split("\\.").length - 1];
    String currentDate = generateCurrentDate("yyyy/MM/dd:HHmm");
    String[] parts = currentDate.split(":");

    String serverPath;
    String fileServerPath;
    try {
      //generate directories on server
      serverPath = FileLocator.toFileURL(filePath).getFile() +
          parts[0];
      File dir = new File(serverPath);
      dir.mkdirs();

      //create new file on server
      fileServerPath = serverPath + "/" + parts[1] + "_" + getNextFileNumber() + "." + fileEnding;
      serverFile = new File(fileServerPath);
      serverFile.createNewFile();

      //write content to new file
      content = IOUtility.getContent(new FileInputStream(file));
      IOUtility.writeContent(new FileOutputStream(serverFile), content, true);

    }
    catch (FileNotFoundException e1) {
      throw new ProcessingException(e1.getMessage());
    }
    catch (IOException e) {
      throw new ProcessingException(e.getMessage());
    }

    return serverFile.getAbsolutePath();
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
}
