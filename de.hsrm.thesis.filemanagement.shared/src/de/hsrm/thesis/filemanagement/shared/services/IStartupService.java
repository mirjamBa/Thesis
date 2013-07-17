package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

/**
 * Service Interface for session initialisation
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IStartupService extends IService {
	public static final String USER_NR = "userNr";

	/**
	 * Sets necessary session data in every Server Session
	 * 
	 * @throws ProcessingException
	 */
	public void initSessionData() throws ProcessingException;
}
