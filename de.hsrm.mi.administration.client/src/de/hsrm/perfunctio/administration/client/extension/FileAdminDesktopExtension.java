package de.hsrm.perfunctio.administration.client.extension;

import java.util.ArrayList;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.perfunctio.administration.client.ui.desktop.outlines.FilemanagementAdminOutline;

public class FileAdminDesktopExtension extends AbstractDesktopExtension{
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends IOutline>[] getConfiguredOutlines() {
		@SuppressWarnings("rawtypes")
		ArrayList<Class> outlines = new ArrayList<Class>();
		outlines.add(FilemanagementAdminOutline.class);
		return outlines.toArray(new Class[outlines.size()]);
	}
	
	
	@Order(20.0)
	  public class AdministrationOutlineViewButton extends AbstractOutlineViewButton {
	    public AdministrationOutlineViewButton() {
	      super(getCoreDesktop(), FilemanagementAdminOutline.class);
	    }

	    @Override
	    protected String getConfiguredText() {
	      return TEXTS.get("Administration");
	    }
	  }

}
