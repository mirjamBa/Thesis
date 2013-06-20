package de.hsrm.thesis.filemanagement.client.services;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;
import org.eclipse.scout.commons.exception.ProcessingException;

import de.hsrm.thesis.filemanagement.shared.services.IFileService;

public class ClientFileService extends AbstractService implements IClientFileService{

	@Override
	public void openFile(Long fileNr) throws ProcessingException{
		File file = SERVICES.getService(IFileService.class).getServerFile(fileNr);

	    Desktop desktop = Desktop.getDesktop();
	    try {
	      desktop.open(file);
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }
	}
}
