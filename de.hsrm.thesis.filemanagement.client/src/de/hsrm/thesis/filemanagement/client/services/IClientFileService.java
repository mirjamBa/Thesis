package de.hsrm.thesis.filemanagement.client.services;

import java.io.File;

import org.eclipse.scout.service.IService;
import org.eclipse.scout.commons.exception.ProcessingException;

public interface IClientFileService extends IService{

	public void openFile(Long fileNr) throws ProcessingException;
	
	public void downloadFile(File f, File tempfile) throws ProcessingException;
}
