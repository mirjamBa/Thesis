package de.hsrm.thesis.filemanagement.client.extension;

import java.util.ArrayList;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.thesis.filemanagement.client.ui.desktop.outlines.FileManagementOutline;

public class FilemanagementDesktopExtension extends AbstractDesktopExtension{
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends IOutline>[] getConfiguredOutlines() {
		@SuppressWarnings("rawtypes")
		ArrayList<Class> outlines = new ArrayList<Class>();
		outlines.add(FileManagementOutline.class);
		return outlines.toArray(new Class[outlines.size()]);
	}
	
	@Order(10.0)
	  public class FileManagementOutlineViewButton extends AbstractOutlineViewButton {
	    public FileManagementOutlineViewButton() {
	      super(getCoreDesktop(), FileManagementOutline.class);
	    }

	    @Override
	    protected String getConfiguredText() {
	      return TEXTS.get("FileManagement");
	    }
	  }

}
