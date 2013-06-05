package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IMetadataService extends IService2 {

  MetadataFormData create(MetadataFormData formData) throws ProcessingException;

  MetadataFormData delete(MetadataFormData formData) throws ProcessingException;

  MetadataFormData update(MetadataFormData formData) throws ProcessingException;

  public Object[][] getAttributes(Long filetypeId) throws ProcessingException;
}
