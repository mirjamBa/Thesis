package de.hsrm.thesis.filemanagement.shared.services;

import java.io.File;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import de.hsrm.thesis.filemanagement.shared.files.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.formdata.FileFormData;
import de.hsrm.thesis.filemanagement.shared.formdata.FileSearchFormData;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFileService extends IService2 {

  FileFormData create(FileFormData formData, ServerFileData fileData) throws ProcessingException;

  FileFormData update(FileFormData formData) throws ProcessingException;

  public Object[][] getFiles(FileSearchFormData searchFormData) throws ProcessingException;

  public ServerFileData saveFile(File file) throws ProcessingException;

  public void openFile(Long fileNr) throws ProcessingException;

  public void updateRoleFilePermission(Long fildId, Long[] roleIds) throws ProcessingException;

  public Object[][] getImages() throws ProcessingException;

  public boolean deleteFile(Long fileId) throws ProcessingException;
}
