package de.hsrm.thesis.filemanagement.shared.services;

import java.util.Set;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

import de.hsrm.thesis.filemanagement.shared.formdata.UserFormData;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IUserProcessService extends IService {

	public static final String USER_ROLE_ADMIN = "Administrator";
	public static final String USER_ROLE_USER = "User";
	static final String PERMISSION_KEY = "permission_id";

	ICode<Integer> getUserPermission(String userName)
			throws ProcessingException;

	Object[][] getUsers() throws ProcessingException;

	void deleteUser(Long[] u_id) throws ProcessingException;

	void createUser(UserFormData formData) throws ProcessingException;

	void updateUser(UserFormData formData) throws ProcessingException;

	public void registerUser() throws ProcessingException;

	public void unregisterUser() throws ProcessingException;

	public Set<String> getUsersOnline() throws ProcessingException;

	public Long[] getUserRoles(Long userId) throws ProcessingException;

	public long getUserId(String username) throws ProcessingException;

	public boolean createNewUser(String username, String password)
			throws ProcessingException;

	public boolean isAdmin(Long[] roles) throws ProcessingException;

	public boolean createNewUser(String username, String password,
			Long[] roleIds) throws ProcessingException;

	public void resetPassword(Long u_Id, String newPassword)
			throws ProcessingException;

	public boolean isValidUser(String username, String password)
			throws ProcessingException;

	public Long getRoleId(String name) throws ProcessingException;

	public void setUserRoles(Long userId, Long[] roleIds)
			throws ProcessingException;

	public Long getAdminRoleId() throws ProcessingException;

	public Long getUserRoleId() throws ProcessingException;

	public void checkUsername(String username) throws VetoException;
	
	public Long getCurrentUserId();

}
