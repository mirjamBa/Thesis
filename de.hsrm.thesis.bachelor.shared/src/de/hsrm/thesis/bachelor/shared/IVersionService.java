package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IVersionService extends IService2 {

  Object[][] getVersionControlOfFiletypes() throws ProcessingException;

  VersionFormData load(VersionFormData formData) throws ProcessingException;

  VersionFormData store(VersionFormData formData) throws ProcessingException;
}
