package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.services.common.pwd.IPasswordManagementService;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;

/**
 * Service Interface for password handling
 * 
 * @author BSI
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IPasswordProcessService extends IPasswordManagementService {

	/**
	 * Validates the password for usage
	 * 
	 * @param password
	 *            String
	 * @throws VetoException
	 */
	public void checkPassword(String password) throws VetoException;
}
