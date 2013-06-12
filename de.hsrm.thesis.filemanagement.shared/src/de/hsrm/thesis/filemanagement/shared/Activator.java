package de.hsrm.thesis.filemanagement.shared;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator implements BundleActivator{

	// The plug-in ID
	public static final String PLUGIN_ID = "de.hsrm.thesis.filemanagement.shared"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	public static Activator getDefault() {
	    return plugin;
	  }

	@Override
	  public void start(BundleContext context) throws Exception {
	    plugin = this;
	  }

	  @Override
	  public void stop(BundleContext context) throws Exception {
	    plugin = null;
	  }
}
