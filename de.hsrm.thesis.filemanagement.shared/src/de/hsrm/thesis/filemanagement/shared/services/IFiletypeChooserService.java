package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import de.hsrm.thesis.filemanagement.shared.formdata.FiletypeChooserFormData;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFiletypeChooserService extends IService2 {

  FiletypeChooserFormData prepareCreate(FiletypeChooserFormData formData) throws ProcessingException;

  FiletypeChooserFormData create(FiletypeChooserFormData formData) throws ProcessingException;

  FiletypeChooserFormData load(FiletypeChooserFormData formData) throws ProcessingException;

  FiletypeChooserFormData store(FiletypeChooserFormData formData) throws ProcessingException;
}
