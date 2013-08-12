package de.hsrm.perfunctio.core.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

/**
 * Service interface for checking permissions
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IPermissionControlService extends IService {

	/**
	 * Checks whether the subject is allowed to execute a function on a
	 * ressource.
	 * 
	 * @param currentUser
	 *            Long
	 * @param resource
	 *            Object
	 * @param function
	 *            String
	 * @return boolean
	 * @throws ProcessingException
	 */
	public boolean check(Long currentUser, Object resource, String function)
			throws ProcessingException;
}
