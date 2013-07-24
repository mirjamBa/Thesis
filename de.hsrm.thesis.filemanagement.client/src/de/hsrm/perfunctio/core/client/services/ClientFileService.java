package de.hsrm.perfunctio.core.client.services;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.file.RemoteFile;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;
import org.eclipse.swt.program.Program;

import de.hsrm.perfunctio.core.client.util.DownloadUtility;
import de.hsrm.perfunctio.core.shared.security.OpenFilePermission;
import de.hsrm.perfunctio.core.shared.services.IFileService;

public class ClientFileService extends AbstractService
		implements
			IClientFileService {
	private IScoutLogger logger = ScoutLogManager.getLogger(ClientFileService.class);

	@Override
	public void openFile(Long fileNr) throws ProcessingException {
		if (!ACCESS.check(new OpenFilePermission())) {
			throw new VetoException(TEXTS.get("VETOOpenFilePermission"));
		}
		File file = SERVICES.getService(IFileService.class).getServerFile(
				fileNr);

		if (UserAgentUtility.isWebClient()) {
			IDesktop desktop = ClientJob.getCurrentSession().getDesktop();
			desktop.openBrowserWindow(file.getAbsolutePath());
		} else {
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
					logger.error(e.getMessage());
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	public void downloadFile(File f, File tempfile) throws ProcessingException {
		String filename = IOUtility.getFileName(f.getAbsolutePath());

		FileOutputStream o;
		if (tempfile != null) {
			RemoteFile rf = new RemoteFile(filename, System.currentTimeMillis());
			try {
				rf.readData(new FileInputStream(f));
				rf.setContentLength((int) f.length());
				rf.setContentTypeByExtension(IOUtility
						.getFileExtension(filename));
				o = new FileOutputStream(tempfile);
				rf.writeData(o);
				// will do nothing when in rich client mode, but show a
				// web
				// save as dialog in web ui
				DownloadUtility.downloadFile(tempfile);
			} catch (FileNotFoundException e1) {
				throw new ProcessingException("Cannot find file " + e1);
			} catch (IOException e1) {
				throw new ProcessingException("Cannot write file " + e1);
			}
		}

	}

}
