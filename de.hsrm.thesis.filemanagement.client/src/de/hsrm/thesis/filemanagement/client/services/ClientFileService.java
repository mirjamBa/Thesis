package de.hsrm.thesis.filemanagement.client.services;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;
import org.eclipse.swt.program.Program;

import de.hsrm.thesis.filemanagement.shared.services.IFileService;

public class ClientFileService extends AbstractService
		implements
			IClientFileService {

	@Override
	public void openFile(Long fileNr) throws ProcessingException {
		// TODO test opening files from server
		File file = SERVICES.getService(IFileService.class).getServerFile(
				fileNr);
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(file);
		} catch (IOException e) {
			try {
				String fileExtension = file.getCanonicalPath().split("\\.")[file
						.getCanonicalPath().split("\\.").length - 1];
				Program program = Program.findProgram(fileExtension);
				program.execute(file.getAbsolutePath());

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
