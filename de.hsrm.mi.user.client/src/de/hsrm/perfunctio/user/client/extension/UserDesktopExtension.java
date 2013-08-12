package de.hsrm.perfunctio.user.client.extension;

import java.util.ArrayList;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.perfunctio.user.client.ui.desktop.outlines.UserOutline;

/**
 * User-Administration-Outline Extension for adding the UserOutline to an
 * existing Scout-Application
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class UserDesktopExtension extends AbstractDesktopExtension {

	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends IOutline>[] getConfiguredOutlines() {
		@SuppressWarnings("rawtypes")
		ArrayList<Class> outlines = new ArrayList<Class>();
		outlines.add(UserOutline.class);
		return outlines.toArray(new Class[outlines.size()]);
	}

	@Order(30.0)
	public class UserOutlineViewButton extends AbstractOutlineViewButton {
		public UserOutlineViewButton() {
			super(getCoreDesktop(), UserOutline.class);
		}

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("User");
		}
	}
}
