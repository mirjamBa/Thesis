package de.hsrm.thesis.filemanagement.client;

import java.util.HashMap;
import java.util.Map;
import java.io.File;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.osgi.framework.BundleContext;

import de.hsrm.thesis.filemanagement.client.handler.HandlerUtility;
import de.hsrm.thesis.filemanagement.client.handler.IHandler;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.hsrm.thesis.filemanagement.client"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private HandlerUtility handlerUtility = new HandlerUtility();
	
	/**
	 * @return the handlerUtility
	 */
	public HandlerUtility getHandlerUtility() {
		return handlerUtility;
	}

	public void addHandler(IHandler handler){
		handlerUtility.addHandler(handler);
	}
	
	public void handle(File dropfile) throws ProcessingException{
		handlerUtility.handle(dropfile);
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

	
	//image cache
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
