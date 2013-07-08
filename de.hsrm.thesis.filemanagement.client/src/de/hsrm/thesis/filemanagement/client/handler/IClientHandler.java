package de.hsrm.thesis.filemanagement.client.handler;

import java.io.File;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;

import de.hsrm.thesis.filemanagement.shared.beans.ServerFileData;

public interface IClientHandler {

	public void handle(File dropfile, ServerFileData fileData, Map<String, String> metaValues,
			String fileformat, Long filetypeNr, Long parentFolderId) throws ProcessingException;

	public void setNext(IClientHandler handler);

}
