package net.sourceforge.tess4j;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		System.loadLibrary("liblept168");
		System.loadLibrary("libtesseract302");
		System.loadLibrary("gsdll32");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}
}
