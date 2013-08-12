package de.hsrm.perfunctio.core.client;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.osgi.framework.BundleContext;

import de.hsrm.perfunctio.core.client.handler.AbstractClientHandler;
import de.hsrm.perfunctio.core.client.handler.ClientHandlerManager;
import de.hsrm.perfunctio.core.client.handler.FileUploadData;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.hsrm.thesis.filemanagement.client"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private ClientHandlerManager handlerManager = new ClientHandlerManager();

	/**
	 * @return the handlerUtility
	 */
	public ClientHandlerManager getHandlerManager() {
		return handlerManager;
	}

	/**
	 * Adds a handler to the end of the handler chain
	 * 
	 * @param handler
	 *            AbstractClientHandler
	 */
	public void appendHandler(AbstractClientHandler handler) {
		handlerManager.appendHandler(handler);
	}

	/**
	 * Adds a handler to the handler chain depending on the priorities
	 * 
	 * @param handler
	 *            AbstractClientHandler
	 */
	public void addHandler(AbstractClientHandler handler) {
		handlerManager.addHandler(handler);
	}

	/**
	 * Activates the handler chain
	 * 
	 * @param data
	 *            FileUploadData
	 * @throws ProcessingException
	 */
	public void handle(FileUploadData data) throws ProcessingException {
		handlerManager.handle(data);
	}

	/**
	 * The constructor
	 */
	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

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

	/**
	 * Image cache
	 * 
	 * @author BSI Business Systems Integration AG
	 * @see <a
	 *      href="http://wiki.eclipse.org/Scout/HowTo/3.8/Display_images_in_a_table_page">http://wiki.eclipse.org/Scout/HowTo/3.8/Display_images_in_a_table_page</a>
	 */
	private Map<String, byte[]> m_imageCache = new HashMap<String, byte[]>();

	public void cacheImage(String imageId, byte[] content) {
		if (content != null) {
			m_imageCache.put(imageId, content);
		}
	}

	public boolean isImageCached(String imageId) {
		return m_imageCache.containsKey(imageId);
	}

	public byte[] getImageFromCache(String imageId) {
		byte[] content = m_imageCache.get(imageId);
		if (content != null && content.length > 0) {
			return content;
		}
		return null;
	}
}
