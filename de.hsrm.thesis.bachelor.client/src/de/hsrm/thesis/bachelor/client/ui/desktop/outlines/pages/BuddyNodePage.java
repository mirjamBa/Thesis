package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;

import de.hsrm.thesis.bachelor.client.ClientSession;
import de.hsrm.thesis.bachelor.client.services.BuddyIconProviderService;
import de.hsrm.thesis.bachelor.client.ui.forms.ChatForm;

public class BuddyNodePage extends AbstractPageWithNodes {

  private String m_name;
  private ChatForm m_form;

  @Override
  protected boolean getConfiguredExpanded() {
    return true;
  }

  @Override
  protected String getConfiguredIconId() {
    return BuddyIconProviderService.BUDDY_ICON_PREFIX + getName();
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected boolean getConfiguredTableVisible() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return getName();
  }

  public void setDefaultFocus() {
    new ClientJob("set focus to message field", ClientSession.get(), true) {
      @Override
      protected void runVoid(IProgressMonitor monitor) throws Throwable {
        getChatForm().getMessageField().requestFocus();
      }
    }.schedule(200);
  }

  public ChatForm getChatForm() throws ProcessingException {
    if (m_form == null) {
      m_form = new ChatForm();
      m_form.setAutoAddRemoveOnDesktop(false);
      m_form.setUserName(ClientSession.get().getUserId());
      m_form.setBuddyName(getName());
      m_form.startNew();
    }
    return m_form;
  }

  @Override
  protected void execPageActivated() throws ProcessingException {
    super.execPageActivated();

    // after buddy page activation the buddy's chat history is displayed on the right side
    ChatForm chatForm = getChatForm();
    setDetailForm(chatForm);
    setDefaultFocus();
  }

  @FormData
  public String getName() {
    return m_name;
  }

  @FormData
  public void setName(String name) {
    m_name = name;
  }
}
