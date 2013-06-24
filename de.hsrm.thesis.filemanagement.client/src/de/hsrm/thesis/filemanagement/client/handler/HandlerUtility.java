package de.hsrm.thesis.filemanagement.client.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;

public class HandlerUtility {

	private List<IHandler> handler;

	public HandlerUtility() {
		handler = new ArrayList<IHandler>();

		FileChooserFormHandler fileChooserFormHandler = new FileChooserFormHandler();
		UnknownFileformatHandler unknownFileFormatHandler = new UnknownFileformatHandler();
		MultipleFiletypesHandler multipleFiletypesHandler = new MultipleFiletypesHandler();
		FileDataHandler fileDataHandler = new FileDataHandler();

		fileChooserFormHandler.setNext(unknownFileFormatHandler);
		unknownFileFormatHandler.setNext(multipleFiletypesHandler);
		multipleFiletypesHandler.setNext(fileDataHandler);

		handler.add(fileChooserFormHandler);
		handler.add(unknownFileFormatHandler);
		handler.add(multipleFiletypesHandler);
		handler.add(fileDataHandler);
	}

	public void addHandler(IHandler newHandler) {
		handler.get(handler.size() - 1).setNext(newHandler);
		handler.add(newHandler);
	}

	public void handle() throws ProcessingException{
		handler.get(0).handle(null, null, null, null);
	}

}
