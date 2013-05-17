package de.hsrm.thesis.bachelor.client.ui.desktop;

import java.util.ArrayList;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ClientSyncJob;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.client.ui.form.outline.DefaultOutlineTableForm;
import org.eclipse.scout.rt.client.ui.form.outline.DefaultOutlineTreeForm;
import org.eclipse.scout.rt.extension.client.ui.desktop.AbstractExtensibleDesktop;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;

import de.hsrm.thesis.bachelor.client.ClientSession;
import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.AdministrationOutline;
import de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages.UserNodePage;
import de.hsrm.thesis.bachelor.shared.Icons;

public class Desktop extends AbstractExtensibleDesktop implements IDesktop {
  private static IScoutLogger logger = ScoutLogManager.getLogger(Desktop.class);

  public Desktop() {
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Class<? extends IOutline>[] getConfiguredOutlines() {
    ArrayList<Class> outlines = new ArrayList<Class>();
    outlines.add(AdministrationOutline.class);
    return outlines.toArray(new Class[outlines.size()]);
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ApplicationTitle");
  }

  @Override
  protected void execOpened() throws ProcessingException {
    // outline tree
    DefaultOutlineTreeForm treeForm = new DefaultOutlineTreeForm();
    treeForm.setIconId(Icons.EclipseScout);
    treeForm.startView();

    //outline table
    DefaultOutlineTableForm tableForm = new DefaultOutlineTableForm();
    tableForm.setIconId(Icons.EclipseScout);
    tableForm.startView();

    if (getAvailableOutlines().length > 0) {
      setOutline(getAvailableOutlines()[0]);
    }

  }

  public static Desktop get() {
    return (Desktop) ClientSyncJob.getCurrentSession().getDesktop();
  }

  public UserNodePage getUserNodePage() {
//    IPage invisibleRootPage = getChatOutline().getRootPage();
//    if (invisibleRootPage != null && invisibleRootPage.getChildNodeCount() > 0) {
//      IPage p = invisibleRootPage.getChildPage(0);
//      if (p instanceof UserNodePage) {
//        return (UserNodePage) p;
//      }
//    }
    return null;
  }

  @Order(10.0)
  public class AboutMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("AboutMenu");
    }

    @Override
    public void execAction() throws ProcessingException {
      ScoutInfoForm form = new ScoutInfoForm();
      form.startModify();
    }
  }

  @Order(20.0)
  public class LogoutMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Logout");
    }

    @Override
    protected void execPrepareAction() throws ProcessingException {
      setVisible(UserAgentUtility.isDesktopDevice());
    }

    @Override
    protected void execAction() throws ProcessingException {
      ClientSession.get().stopSession();
    }
  }

  @Order(10.0)
  public class FileMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("FileMenu");
    }

    @Order(100.0)
    public class ExitMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ExitMenu");
      }

      @Override
      public void execAction() throws ProcessingException {
        ClientSyncJob.getCurrentSession(ClientSession.class).stopSession();
      }
    }
  }

  @Order(10.0)
  public class RefreshOutlineKeyStroke extends AbstractKeyStroke {

    @Override
    protected String getConfiguredKeyStroke() {
      return "f5";
    }

    @Override
    protected void execAction() throws ProcessingException {
      if (getOutline() != null) {
        IPage page = getOutline().getActivePage();
        if (page != null) {
          page.reloadPage();
        }
      }
    }
  }

  @Order(10.0)
  public class AdministrationOutlineViewButton extends AbstractOutlineViewButton {
    public AdministrationOutlineViewButton() {
      super(Desktop.this, AdministrationOutline.class);
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Administration");
    }
  }
}
