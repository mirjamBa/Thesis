package de.hsrm.perfunctio.core.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

/**
 * Service Interface for file type handling
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFiletypeService extends IService {

	/**
	 * Fetches all available file types from storage [file type id, version
	 * control]
	 * 
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getFiletypes() throws ProcessingException;

}
