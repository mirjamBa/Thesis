package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IStorageService extends IService2 {

	/**
	 * Creates a new storage for all application data
	 * 
	 * @throws ProcessingException
	 */
	public void installStorage() throws ProcessingException;

}
