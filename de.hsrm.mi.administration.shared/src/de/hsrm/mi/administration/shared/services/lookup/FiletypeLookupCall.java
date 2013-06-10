package de.hsrm.mi.administration.shared.services.lookup;

import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import de.hsrm.mi.administration.shared.services.lookup.IFiletypeLookupService;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

public class FiletypeLookupCall extends LookupCall{

  private static final long serialVersionUID = 1L;

  @Override
  protected Class<? extends ILookupService> getConfiguredService() {
    return IFiletypeLookupService.class;
  }
}
