package de.hsrm.thesis.filemanagement.server;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.osgi.framework.BundleContext;

import de.hsrm.thesis.filemanagement.server.handler.IServerHandler;
import de.hsrm.thesis.filemanagement.server.handler.ServerHandlerManager;
import de.hsrm.thesis.filemanagement.shared.beans.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.services.formdata.FileFormData;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.hsrm.thesis.filemanagement.server"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private ServerHandlerManager handlerManager = new ServerHandlerManager();

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/**
	 * @return the handlerUtility
	 */
	public ServerHandlerManager getHandlerManager() {
		return handlerManager;
	}

	public void addHandler(IServerHandler handler) {
		handlerManager.addHandler(handler);
	}

	public void handle(Long fileId, FileFormData formData, ServerFileData fileData,
			Long parentFolderId) throws ProcessingException {
		if (handlerManager.hasHandler()) {
			handlerManager.handle(fileId, formData, fileData, parentFolderId);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
