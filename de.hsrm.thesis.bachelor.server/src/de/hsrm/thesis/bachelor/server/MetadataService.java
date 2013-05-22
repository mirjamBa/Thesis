package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.CreateMetadataPermission;
import de.hsrm.thesis.bachelor.shared.IMetadataService;
import de.hsrm.thesis.bachelor.shared.MetadataFormData;
import de.hsrm.thesis.bachelor.shared.ReadMetadataPermission;
import de.hsrm.thesis.bachelor.shared.UpdateMetadataPermission;

public class MetadataService extends AbstractService implements IMetadataService {

  @Override
  public MetadataFormData prepareCreate(MetadataFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateMetadataPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public MetadataFormData create(MetadataFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateMetadataPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here.
    return formData;
  }

  @Override
  public MetadataFormData load(MetadataFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadMetadataPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public MetadataFormData store(MetadataFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateMetadataPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }
}
