package de.hsrm.perfunctio.core.client.util;

import java.io.File;

import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.IFileChooserField;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;

/**
 * 
 * @author Ivan Motsch
 * @url http://www.eclipse.org/forums/index.php/t/366585/
 *
 */
public class DownloadUtility {
	
	/**
	   * Used in web ui to force a file download for the user.
	   * <p>
	   * This is normally used on {@link IFileChooserField}s with {@link IFileChooserField#isTypeLoad()}=false.
	   * <p>
	   * In web ui the file chooser in save mode creates a temp file. The temp file is written by bsi crm logic and finally
	   * this utility method downloads the temp file to the end user.
	   */
	  public static void downloadFile(File tempFile) {
	    if (!UserAgentUtility.isWebClient()) {
	      return;
	    }
	    IDesktop desktop = ClientJob.getCurrentSession().getDesktop();
	    if (!desktop.isGuiAvailable()) {
	      return;
	    }
	    desktop.openBrowserWindow(tempFile.getAbsolutePath());
	  }

}
