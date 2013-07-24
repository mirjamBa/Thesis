package de.hsrm.perfunctio.core.server.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;

import de.hsrm.perfunctio.core.shared.beans.ServerFileData;
import de.hsrm.perfunctio.core.shared.services.formdata.FileFormData;

public class ServerHandlerManager {

	private List<IServerHandler> handler;

	public ServerHandlerManager() {
		handler = new ArrayList<IServerHandler>();
	}

	public void addHandler(IServerHandler newHandler) {
		if (hasHandler()) {
			handler.get(handler.size() - 1).setNext(newHandler);
		}
		handler.add(newHandler);
	}

	public void handle(Long fileId, FileFormData formData, ServerFileData fileData,
			Long parentFolderId) throws ProcessingException {
		handler.get(0).handle(fileId, formData, fileData, parentFolderId);
	}

	public boolean hasHandler() {
		return handler.size() > 0;
	}

}
