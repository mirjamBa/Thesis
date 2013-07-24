package de.hsrm.thesis.bachelor.client.ui.desktop;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ClientSyncJob;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.IDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.client.ui.form.outline.DefaultOutlineTableForm;
import org.eclipse.scout.rt.client.ui.form.outline.DefaultOutlineTreeForm;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.desktop.AbstractExtensibleDesktop;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.administration.client.extension.FileAdminDesktopExtension;
import de.hsrm.perfunctio.core.client.extension.FilemanagementDesktopExtension;
import de.hsrm.perfunctio.core.shared.Icons;
import de.hsrm.perfunctio.user.client.extension.UserDesktopExtension;
import de.hsrm.thesis.bachelor.client.ClientSession;
import de.hsrm.thesis.bachelor.shared.services.IOCRProcessService;

public class Desktop extends AbstractExtensibleDesktop implements IDesktop {
  private static IScoutLogger logger = ScoutLogManager.getLogger(Desktop.class);

  public Desktop() {
  }

  @Override
  protected void injectDesktopExtensions(List<IDesktopExtension> desktopExtensions) {
    FilemanagementDesktopExtension fileExtension = new FilemanagementDesktopExtension();
    fileExtension.setCoreDesktop(this);
    desktopExtensions.add(fileExtension);

    FileAdminDesktopExtension extension = new FileAdminDesktopExtension();
    extension.setCoreDesktop(this);
    desktopExtensions.add(extension);

    UserDesktopExtension userExtension = new UserDesktopExtension();
    userExtension.setCoreDesktop(this);
    desktopExtensions.add(userExtension);

  }

  @SuppressWarnings("unchecked")
  @Override
  protected Class<? extends IOutline>[] getConfiguredOutlines() {
    ArrayList<Class> outlines = new ArrayList<Class>();
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

  @Order(30.0)
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

  @Order(20.0)
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

  @Order(40.0)
  public class OCRTestingMenu extends AbstractExtensibleMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("OCRTesting");
    }

    @Override
    protected void execAction() throws ProcessingException {
      SERVICES.getService(IOCRProcessService.class).doOCR();
    }
  }

}
