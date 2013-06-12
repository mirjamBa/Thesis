package de.hsrm.thesis.bachelor.server.services;

import java.awt.Desktop;
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
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringHolder;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;
import org.osgi.framework.FrameworkUtil;

import de.hsrm.mi.administration.shared.services.ITagService;
import de.hsrm.thesis.bachelor.server.ServerSession;
import de.hsrm.thesis.bachelor.server.util.TagUtility;
import de.hsrm.thesis.bachelor.server.util.UserUtility;
import de.hsrm.thesis.filemanagement.shared.files.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.formdata.FileFormData;
import de.hsrm.thesis.filemanagement.shared.formdata.FileSearchFormData;
import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;

public class FileService extends AbstractService implements IFileService {

  @Override
  public FileFormData create(FileFormData formData, ServerFileData fileData) throws ProcessingException {
    //insert file
    SQL.insert("INSERT INTO file (number, filetype_id, file_path, u_id) VALUES (:number, :filetype_id, :filePath, :u_id)",
        new NVPair("number", fileData.getNumber()),
        new NVPair("filetype_id", formData.getType().getValue()),
        new NVPair("filePath", fileData.getPath()),
        new NVPair("u_id", UserUtility.getUserId(ServerSession.get().getUserId())));

    //get generated file_id
    LongHolder fileId = new LongHolder();
    SQL.selectInto("SELECT file_id FROM file WHERE number = :number INTO :fileId",
        new NVPair("number", fileData.getNumber()),
        new NVPair("fileId", fileId));

    //check for available tags
    if (TagUtility.tagsExisting()) {
      //insert tag-file connection
      Long[] selectedTags = formData.getAvailableTagsBox().getValue();
      if (selectedTags != null) {
        for (Long tagId : selectedTags) {
          createTagFileConnection(fileId.getValue(), tagId);
        }
      }
    }

    //check for new Tags
    String newTags = formData.getNewTag().getValue();
    if (newTags != null) {
      String[] tags = TagUtility.filterTagnames(newTags);
      SERVICES.getService(ITagService.class).create(tags);

      //connect new tags to new file
      for (String s : tags) {
        Long tagId = TagUtility.getTagId(s);
        createTagFileConnection(fileId.getValue(), tagId);
      }
    }

    //insert role-file connecntion
    Long[] selectedRoles = formData.getRoles().getValue();
    if (selectedRoles != null) {
      for (Long roleId : selectedRoles) {
        SQL.insert("INSERT INTO role_file_permission (file_id, role_id) VALUES (:fileId, :roleId)",
            new NVPair("fileId", fileId.getValue()),
            new NVPair("roleId", roleId));
      }
    }

    return formData;
  }

  private void createTagFileConnection(Long fileId, Long tagId) throws ProcessingException {
    SQL.insert("INSERT INTO tag_file (tag_id, file_id) VALUES (:tagId, :fileId)",
        new NVPair("tagId", tagId),
        new NVPair("fileId", fileId));
  }

  @Override
  public FileFormData update(FileFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public Object[][] getImages() throws ProcessingException {
    FileSearchFormData data = new FileSearchFormData();
    data.getFileType().setValue(FileTypeCodeType.ImageCode.ID);
    return getFiles(data);
  }

  @Override
  public Object[][] getFiles(FileSearchFormData searchFormData) throws ProcessingException {
    Long user_id = ServerSession.get().getUserNr();

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("SELECT F.FILE_ID, "
        + "            F.FILETYPE_ID, "
        + "            F.NUMBER, "
        + "            F.U_ID, "
        + "            F.FILE_PATH "
        + "     FROM   FILE F "
        + "     WHERE  1 = 1 "
        + "     AND    (F.U_ID = :userId " //visible for the typist of the file
        + "     OR     F.FILE_ID IN "
        + "             (SELECT RF.FILE_ID FROM ROLE_FILE_PERMISSION RF WHERE RF.ROLE_ID IN " //visible for users who are a member of those roles, the files are approved for
        + "                     (SELECT R.ROLE_ID FROM USER_ROLE R WHERE R.U_ID = :userId) "
        + "             ) "
        + "             ) ");

    if (searchFormData.getFileType().getValue() != null) {
      stringBuilder.append(" AND F.FILETYPE_ID = :fileType ");
    }

    return SQL.select(stringBuilder.toString(), new NVPair("userId", user_id), searchFormData);
  }

  @Override
  public ServerFileData saveFile(File file) throws ProcessingException {
    URL filePath = FrameworkUtil.getBundle(getClass()).getResource("files/");
    byte[] content;
    File serverFile;

    String fileEnding = file.getName().split("\\.")[file.getName().split("\\.").length - 1];
    String currentDate = generateCurrentDate("yyyy/MM/dd:HHmm");
    String[] parts = currentDate.split(":");

    String serverPath;
    String fileServerPath;

    Long nextFileNr = getNextFileNumber();

    try {
      //generate directories on server
      serverPath = FileLocator.toFileURL(filePath).getFile() +
          parts[0];
      File dir = new File(serverPath);
      dir.mkdirs();

      //create new file on server
      fileServerPath = serverPath + "/" + parts[1] + "_" + nextFileNr + "." + fileEnding;
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
    ServerFileData fileData = new ServerFileData(serverFile.getAbsolutePath(), nextFileNr, serverFile.length());

    return fileData;
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

  private File getServerFile(Long fileNr) throws ProcessingException {
    StringHolder path = new StringHolder();
    SQL.selectInto("SELECT file_path FROM file WHERE file_id = :fileNr INTO :path",
        new NVPair("fileNr", fileNr),
        new NVPair("path", path));

    return new File(path.getValue());

  }

  @Override
  public void openFile(Long fileNr) throws ProcessingException {
    File file = getServerFile(fileNr);

    Desktop desktop = Desktop.getDesktop();
    try {
      desktop.open(file);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateRoleFilePermission(Long fildId, Long[] roleIds) throws ProcessingException {

    SQL.delete("DELETE FROM role_file_permission WHERE file_id= :fileId", new NVPair("fileId", fildId));

    for (Long roleId : roleIds) {
      SQL.insert("INSERT INTO role_file_permission (file_id, role_id) VALUES (:fileId, :roleId)",
          new NVPair("fileId", fildId),
          new NVPair("roleId", roleId));
    }
  }

  @Override
  public boolean deleteFile(Long fileId) throws ProcessingException {
    //remove tag-file-connection
    SQL.delete("DELETE FROM tag_file WHERE file_id = :fileId", new NVPair("fileId", fileId));
    //remove unused permissions for file
    SQL.delete("DELETE FROM role_file_permission WHERE file_id = :fileId", new NVPair("fileId", fileId));

    //save filepath from database
    StringHolder path = new StringHolder();
    SQL.selectInto("SELECT file_path FROM file WHERE file_id = :fileId INTO :path",
        new NVPair("fileId", fileId),
        new NVPair("path", path));

    //remove filedata in database
    SQL.delete("DELETE FROM metadata WHERE file_id = :fileId", new NVPair("fileId", fileId));
    SQL.delete("DELETE FROM version WHERE file_id = :fileId", new NVPair("fileId", fileId));
    SQL.delete("DELETE FROM file WHERE file_id = :fileId", new NVPair("fileId", fileId));

    //remove file from server
    System.out.println(path.getValue());
    File file = new File(path.getValue());
    return file.delete();
  }
}
