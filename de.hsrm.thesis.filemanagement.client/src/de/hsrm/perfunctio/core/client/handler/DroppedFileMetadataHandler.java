package de.hsrm.perfunctio.core.client.handler;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.beans.ServerFileData;
import de.hsrm.perfunctio.core.shared.services.IFileStorageService;
import de.hsrm.perfunctio.core.shared.services.IMetadataExtractionService;

/**
 * Concrete client handler, responsible for extracting meta data from dropped
 * files
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class DroppedFileMetadataHandler extends AbstractClientHandler {
	private final int PRIORITY = 200;

	public DroppedFileMetadataHandler() {
		this.priority = PRIORITY;
	}

	@Override
	public void handle(FileUploadData data) throws ProcessingException {
		if (data.getFile() != null) {
			// extract data
			ServerFileData fileData = SERVICES.getService(
					IFileStorageService.class)
					.getServerFileData(data.getFile());
			data.setServerFileData(fileData);
			data.setFileFormat(IOUtility.getFileExtension(data.getFile()
					.getName()));
			data.setMetaValues(SERVICES.getService(
					IMetadataExtractionService.class).extractDataFromFile(
					data.getFile()));
		}

		if (nextHandler != null) {
			((AbstractClientHandler) nextHandler).handle(data);
		}

	}

}
