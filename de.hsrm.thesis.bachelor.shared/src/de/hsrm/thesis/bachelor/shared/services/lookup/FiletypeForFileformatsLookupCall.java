package de.hsrm.thesis.bachelor.shared.services.lookup;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.commons.annotations.FormData;

public class FiletypeForFileformatsLookupCall extends LookupCall {

  private static final long serialVersionUID = 1L;
  private String m_fileformat;
  @Override
  protected Class<? extends ILookupService> getConfiguredService() {
    return IFiletypeForFileformatsLookupService.class;
  }
  @FormData
  public String getFileformat() {
    return m_fileformat;
  }
  @FormData
  public void setFileformat(String fileformat) {
    m_fileformat = fileformat;
  }
}
