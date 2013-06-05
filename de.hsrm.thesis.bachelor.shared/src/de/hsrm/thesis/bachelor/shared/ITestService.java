package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface ITestService extends IService2 {

  TestFormData prepareCreate(TestFormData formData) throws ProcessingException;

  TestFormData create(TestFormData formData) throws ProcessingException;

  TestFormData load(TestFormData formData) throws ProcessingException;

  TestFormData store(TestFormData formData) throws ProcessingException;
}
