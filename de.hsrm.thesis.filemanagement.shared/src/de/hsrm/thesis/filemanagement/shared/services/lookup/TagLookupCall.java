package de.hsrm.thesis.filemanagement.shared.services.lookup;

import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import de.hsrm.thesis.filemanagement.shared.services.lookup.ITagLookupService;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

public class TagLookupCall extends LookupCall{

  private static final long serialVersionUID = 1L;

  @Override
  protected Class<? extends ILookupService> getConfiguredService() {
    return ITagLookupService.class;
  }
}
