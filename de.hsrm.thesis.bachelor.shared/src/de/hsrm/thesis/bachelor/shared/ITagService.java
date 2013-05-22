package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface ITagService extends IService2 {

  TagFormData prepareCreate(TagFormData formData) throws ProcessingException;

  TagFormData create(TagFormData formData) throws ProcessingException;

  TagFormData load(TagFormData formData) throws ProcessingException;

  TagFormData store(TagFormData formData) throws ProcessingException;
}
