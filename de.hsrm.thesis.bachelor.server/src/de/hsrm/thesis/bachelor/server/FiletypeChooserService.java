package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.FiletypeChooserFormData;
import de.hsrm.thesis.bachelor.shared.IFiletypeChooserService;

public class FiletypeChooserService extends AbstractService implements IFiletypeChooserService {

  @Override
  public FiletypeChooserFormData prepareCreate(FiletypeChooserFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public FiletypeChooserFormData create(FiletypeChooserFormData formData) throws ProcessingException {
    //TODO [mba] business logic here.
    return formData;
  }

  @Override
  public FiletypeChooserFormData load(FiletypeChooserFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public FiletypeChooserFormData store(FiletypeChooserFormData formData) throws ProcessingException {
    //TODO [mba] business logic here
    return formData;
  }
}
