package de.hsrm.perfunctio.core.client.handler;

import org.eclipse.scout.commons.exception.ProcessingException;

public interface IClientHandler {

	public void handle(FileUploadData data) throws ProcessingException;

	public void setNext(IClientHandler handler);

}
