package de.hsrm.perfunctio.core.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

import de.hsrm.perfunctio.core.shared.services.formdata.UserFormData;

/**
 * Service Interface for user handling
 * 
 * @see <a
 *      href="https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo/blob/d95a1816cc0d362fffa23da7fdab2626962e8467/bahbah/org.eclipse.scout.bahbah.shared/src/org/eclipse/scout/bahbah/shared/services/process/IUserProcessService.java">https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo</a>
 * @author BSI, Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IUserProcessService extends IService {

	static final String PERMISSION_KEY = "permission_id";

	// @author BSI //////////////////////////////

	Object[][] getUsers() throws ProcessingException;

	void deleteUser(Long[] u_id) throws ProcessingException;

	void createUser(UserFormData formData) throws ProcessingException;

	void updateUser(UserFormData formData) throws ProcessingException;

	public boolean isValidUser(String username, String password)
			throws ProcessingException;

	public void checkUsername(String username) throws VetoException;

	public void resetPassword(Long u_Id, String newPassword)
			throws ProcessingException;
	// @author Mirjam Bayatloo////////////////////

	/**
	 * Fetches all role ids the user is a member of
	 * 
	 * @param userId
	 *            Long
	 * @return Long[]
	 * @throws ProcessingException
	 */
	public Long[] getUserRoles(Long userId) throws ProcessingException;

	/**
	 * Fetches the user id for a user
	 * 
	 * @param username
	 *            String
	 * @return long
	 * @throws ProcessingException
	 */
	public long getUserId(String username) throws ProcessingException;

	/**
	 * Saves new user into storage
	 * 
	 * @param username
	 *            String
	 * @param password
	 *            String
	 * @param roleIds
	 *            Long[]
	 * @return boolean
	 * @throws ProcessingException
	 */
	public boolean createNewUser(String username, String password,
			Long[] roleIds) throws ProcessingException;

	/**
	 * Assigns roles to one user
	 * 
	 * @param userId
	 *            Long
	 * @param roleIds
	 *            Long[]
	 * @throws ProcessingException
	 */
	public void setUserRoles(Long userId, Long[] roleIds)
			throws ProcessingException;

	/**
	 * Fetches the user id of the session user
	 * 
	 * @return Long
	 */
	public Long getCurrentUserId();

	/**
	 * Returns the username of the session user
	 * 
	 * @return
	 */
	public String getCurrentUserName();

}
