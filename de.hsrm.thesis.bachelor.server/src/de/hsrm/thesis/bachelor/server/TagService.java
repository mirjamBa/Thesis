package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.CreateTagPermission;
import de.hsrm.thesis.bachelor.shared.ITagService;
import de.hsrm.thesis.bachelor.shared.ReadTagPermission;
import de.hsrm.thesis.bachelor.shared.TagFormData;
import de.hsrm.thesis.bachelor.shared.UpdateTagPermission;

public class TagService extends AbstractService implements ITagService {

  @Override
  public TagFormData prepareCreate(TagFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateTagPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public TagFormData create(TagFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateTagPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here.
    return formData;
  }

  @Override
  public TagFormData load(TagFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadTagPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public TagFormData store(TagFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateTagPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }
}
