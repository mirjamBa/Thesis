package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

/**
 * 
 * @author BSI
 *
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IPasswordProcessService extends IService {

	/**
	 * Validates the password for usage
	 * 
	 * @param password
	 *            String
	 * @throws VetoException
	 */
	public void checkPassword(String password) throws VetoException;
}
