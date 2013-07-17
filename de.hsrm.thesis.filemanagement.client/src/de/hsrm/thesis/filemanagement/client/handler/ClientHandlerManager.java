package de.hsrm.thesis.filemanagement.client.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;

public class ClientHandlerManager {

	private List<IClientHandler> handler;
	private FileChooserFormHandler fileChooserFormHandler;
	private DroppedFileMetadataHandler droppedFileMetadataHander;
	private UnknownFileformatHandler unknownFileFormatHandler;
	private MultipleFiletypesHandler multipleFiletypesHandler;
	private FileDataHandler fileDataHandler;

	public ClientHandlerManager() {
		handler = new ArrayList<IClientHandler>();

		fileChooserFormHandler = new FileChooserFormHandler();
		droppedFileMetadataHander = new DroppedFileMetadataHandler();
		unknownFileFormatHandler = new UnknownFileformatHandler();
		multipleFiletypesHandler = new MultipleFiletypesHandler();
		fileDataHandler = new FileDataHandler();

		fileChooserFormHandler.setNext(droppedFileMetadataHander);
		droppedFileMetadataHander.setNext(unknownFileFormatHandler);
		unknownFileFormatHandler.setNext(multipleFiletypesHandler);
		multipleFiletypesHandler.setNext(fileDataHandler);

		handler.add(fileChooserFormHandler);
		handler.add(droppedFileMetadataHander);
		handler.add(unknownFileFormatHandler);
		handler.add(multipleFiletypesHandler);
		handler.add(fileDataHandler);
	}

	/**
	 * @return the fileChooserFormHandler
	 */
	public FileChooserFormHandler getFileChooserFormHandler() {
		return fileChooserFormHandler;
	}

	/**
	 * @return the droppedFileMetadataHander
	 */
	public DroppedFileMetadataHandler getDroppedFileMetadataHander() {
		return droppedFileMetadataHander;
	}

	/**
	 * @return the unknownFileFormatHandler
	 */
	public UnknownFileformatHandler getUnknownFileFormatHandler() {
		return unknownFileFormatHandler;
	}

	/**
	 * @return the multipleFiletypesHandler
	 */
	public MultipleFiletypesHandler getMultipleFiletypesHandler() {
		return multipleFiletypesHandler;
	}

	/**
	 * @return the fileDataHandler
	 */
	public FileDataHandler getFileDataHandler() {
		return fileDataHandler;
	}

	public void addHandler(IClientHandler newHandler) {
		handler.get(handler.size() - 1).setNext(newHandler);
		handler.add(newHandler);
	}

	public void handle(FileUploadData data) throws ProcessingException {
		handler.get(0).handle(data);
	}

}
