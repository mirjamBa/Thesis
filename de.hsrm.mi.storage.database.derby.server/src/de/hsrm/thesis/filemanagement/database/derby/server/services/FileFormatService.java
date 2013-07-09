package de.hsrm.thesis.filemanagement.database.derby.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.IntegerHolder;
import org.eclipse.scout.commons.holders.LongArrayHolder;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.filemanagement.shared.security.CreateFileFormatPermission;
import de.hsrm.thesis.filemanagement.shared.security.DeleteFileFormatPermission;
import de.hsrm.thesis.filemanagement.shared.security.ReadFileFormatPermission;
import de.hsrm.thesis.filemanagement.shared.security.UpdateFileFormatPermission;
import de.hsrm.thesis.filemanagement.shared.services.IFileFormatService;
import de.hsrm.thesis.filemanagement.shared.services.formdata.FileFormatFormData;

public class FileFormatService extends AbstractService implements IFileFormatService {

  @Override
  public FileFormatFormData create(FileFormatFormData formData) throws ProcessingException {
	  if(!ACCESS.check(new CreateFileFormatPermission())){
		  throw new VetoException(TEXTS.get("VETOCreateFileFormatPermission"));
	  }
    SQL.insert("INSERT INTO fileformat (format, filetype_id) VALUES (:format, :filetype_id)",
        new NVPair("format", formData.getFileFormat().getValue()),
        new NVPair("filetype_id", formData.getFileType().getValue()));
    return formData;
  }

  @Override
  public void delete(Long[] ids) throws ProcessingException {
	  if(!ACCESS.check(new DeleteFileFormatPermission())){
		  throw new VetoException(TEXTS.get("VETODeleteFileFormatPermission"));
	  }
    SQL.delete("DELETE FROM fileformat WHERE fileformat_id = :ids",
        new NVPair("ids", ids));
  }

  @Override
  public FileFormatFormData update(FileFormatFormData formData) throws ProcessingException {
	  if(!ACCESS.check(new UpdateFileFormatPermission())){
		  throw new VetoException(TEXTS.get("VETOUpdateFileFormatPermission"));
	  }
    SQL.update("UPDATE fileformat "
        + "     SET format = :format, "
        + "         filetype_id = :filetype_id "
        + "     WHERE fileformat_id = :formatId",
        new NVPair("format", formData.getFileFormat().getValue()),
        new NVPair("filetype_id", formData.getFileType().getValue()),
        new NVPair("formatId", formData.getId()));
    return formData;
  }

  @Override
  public Object[][] getFileFormats(Long filetypeNr) throws ProcessingException {
	  if(!ACCESS.check(new ReadFileFormatPermission())){
		  throw new VetoException(TEXTS.get("VETOReadFileFormatPermission"));
	  }
    if (filetypeNr == null) {
      return SQL.select("SELECT fileformat_id, "
          + "                   format, "
          + "                   filetype_id "
          + "            FROM fileformat");
    }
    else {
      return SQL.select("SELECT fileformat_id, format, filetype_id FROM fileformat WHERE filetype_id = :filetypeId",
          new NVPair("filetypeId", filetypeNr));
    }
  }

  @Override
  public boolean isFormatMultipleAssigned(String fileformat) throws ProcessingException {
    return getFileformatCount(fileformat) > 1;
  }

  @Override
  public Long getFiletypeForFileFormat(String fileformat) throws ProcessingException {
    LongHolder filetype = new LongHolder();
    SQL.selectInto("SELECT filetype_id FROM fileformat WHERE UPPER(format) = UPPER(:fileformat) INTO :filetype ",
        new NVPair("fileformat", fileformat),
        new NVPair("filetype", filetype));

    return filetype.getValue();
  }

  private int getFileformatCount(String fileformat) throws ProcessingException {
    IntegerHolder countFormats = new IntegerHolder();

    SQL.selectInto("SELECT COUNT(fileformat_id) FROM fileformat WHERE UPPER(format) LIKE UPPER(:fileformat) INTO :countFormats",
        new NVPair("fileformat", fileformat),
        new NVPair("countFormats", countFormats));

    return countFormats.getValue();
  }

  @Override
  public boolean isFileformatRegistered(String fileformat) throws ProcessingException {
    return getFileformatCount(fileformat) > 0;
  }

  @Override
  public Long[] getFiletypesForFileFormat(String fileformat) throws ProcessingException {
    LongArrayHolder ids = new LongArrayHolder();
    SQL.selectInto("SELECT filetype_id FROM fileformat WHERE UPPER(format) = UPPER(:fileformat) INTO :filetype ",
        new NVPair("fileformat", fileformat),
        new NVPair("filetype", ids));

    return ids.getValue();
  }
}
