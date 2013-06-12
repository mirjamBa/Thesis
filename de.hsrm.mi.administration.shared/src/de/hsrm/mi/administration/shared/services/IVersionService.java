package de.hsrm.mi.administration.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import de.hsrm.mi.administration.shared.services.formdata.VersionFormData;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IVersionService extends IService2 {

  public VersionFormData updateFileTypeVersionControl(VersionFormData formData) throws ProcessingException;

}
