package de.hsrm.perfunctio.core.shared.services;

import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.services.common.pwd.IPasswordManagementService;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;

/**
 * Service Interface for password handling
 * 
 * @see <a
 *      href="https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo/blob/d95a1816cc0d362fffa23da7fdab2626962e8467/bahbah/org.eclipse.scout.bahbah.shared/src/org/eclipse/scout/bahbah/shared/services/process/IPasswordProcessService.java">https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo</a>
 * @author BSI Business Systems Integrations AG, Mirjam Bayatloo
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
