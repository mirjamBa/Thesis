package de.hsrm.thesis.filemanagement.client;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.hsrm.thesis.filemanagement.client"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
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

	
//	//image cache
//	  private Map<String, byte[]> m_imageCache = new HashMap<String, byte[]>();
//
//	  public void cacheImage(String imageId, byte[] content) {
//	    if (content != null) {
//	      m_imageCache.put(imageId, content);
//	    }
//	  }
//
//	  public boolean isImageCached(String imageId) {
//	    return m_imageCache.containsKey(imageId);
//	  }
//
//	  public byte[] getImageFromCache(String imageId) {
//	    byte[] content = m_imageCache.get(imageId);
//	    if (content != null && content.length > 0) {
//	      return content;
//	    }
//	    return null;
//	  }
}
