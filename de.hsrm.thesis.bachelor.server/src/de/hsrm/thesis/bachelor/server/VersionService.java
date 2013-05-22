package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.CreateVersionPermission;
import de.hsrm.thesis.bachelor.shared.IVersionService;
import de.hsrm.thesis.bachelor.shared.ReadVersionPermission;
import de.hsrm.thesis.bachelor.shared.UpdateVersionPermission;
import de.hsrm.thesis.bachelor.shared.VersionFormData;

public class VersionService extends AbstractService implements IVersionService {

  @Override
  public VersionFormData prepareCreate(VersionFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateVersionPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public VersionFormData create(VersionFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateVersionPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here.
    return formData;
  }

  @Override
  public VersionFormData load(VersionFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadVersionPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public VersionFormData store(VersionFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateVersionPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }
}
