package de.hsrm.thesis.filemanagement.client.handler;

import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;

import de.hsrm.thesis.filemanagement.shared.nonFormdataBeans.ServerFileData;

public interface IHandler {

	public void handle(ServerFileData fileData, Map<String, String> metaValues,
			String fileformat, Long filetypeNr) throws ProcessingException;

	public void setNext(IHandler handler);

}
