package de.hsrm.thesis.filemanagement.shared.services;

import java.util.Set;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

import de.hsrm.thesis.filemanagement.shared.formdata.UserFormData;

/**
 * 
 * @author BSI, Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IUserProcessService extends IService {

	static final String PERMISSION_KEY = "permission_id";

	// ///////////////////////////@author
	// BSI/////////////////////////////////////////////

	ICode<Integer> getUserPermission(String userName)
			throws ProcessingException;

	Object[][] getUsers() throws ProcessingException;

	void deleteUser(Long[] u_id) throws ProcessingException;

	void createUser(UserFormData formData) throws ProcessingException;

	void updateUser(UserFormData formData) throws ProcessingException;

	public void registerUser() throws ProcessingException;

	public void unregisterUser() throws ProcessingException;

	public Set<String> getUsersOnline() throws ProcessingException;

	public boolean isValidUser(String username, String password)
			throws ProcessingException;

	public void checkUsername(String username) throws VetoException;

	public void resetPassword(Long u_Id, String newPassword)
			throws ProcessingException;
	// ///////////////////////@author Mirjam
	// Bayatloo/////////////////////////////////////

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

}
