package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IMetadataService extends IService2 {

  MetadataFormData prepareCreate(MetadataFormData formData) throws ProcessingException;

  MetadataFormData create(MetadataFormData formData) throws ProcessingException;

  MetadataFormData load(MetadataFormData formData) throws ProcessingException;

  MetadataFormData store(MetadataFormData formData) throws ProcessingException;
}
