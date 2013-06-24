package de.hsrm.thesis.bachelor.client;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import de.hsrm.thesis.bachelor.client.handler.EmbeddedHandler;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

  // The plug-in ID
  public static final String PLUGIN_ID = "de.hsrm.thesis.bachelor.client";

  // The shared instance
  private static Activator plugin;

  /**
   * The constructor
   */
  public Activator() {
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    plugin = this;

    de.hsrm.mi.administration.client.Activator.getDefault().addHandler(new EmbeddedHandler());
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
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
