package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

import de.hsrm.thesis.filemanagement.shared.services.formdata.RoleFormData;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IRoleProcessService extends IService {

	public static final String USER_ROLE_ADMIN = "Administrator";
	public static final String USER_ROLE_USER = "User";

	/**
	 * Fetches all roles from storage [role id, name]
	 * 
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getRoles() throws ProcessingException;

	/**
	 * Saves new role into storage
	 * 
	 * @param formData
	 *            RoleFormData
	 * @return RoleFormData
	 * @throws ProcessingException
	 */
	public RoleFormData create(RoleFormData formData)
			throws ProcessingException;

	/**
	 * Fetches all role ids, the assigned file is freed for
	 * 
	 * @param fileId
	 *            Long
	 * @return Long[]
	 * @throws ProcessingException
	 */
	public Long[] getApprovedRolesForFileOrFolder(Long fileId)
			throws ProcessingException;

	/**
	 * Deletes all roles with one of the assigned role ids
	 * 
	 * @param roleIds
	 * @throws ProcessingException
	 */
	public void deleteRoles(Long[] roleIds) throws ProcessingException;

	/**
	 * Modifies a role in the storage
	 * 
	 * @param formData
	 *            RoleFormData
	 * @return RoleFormData
	 * @throws ProcessingException
	 */
	public RoleFormData update(RoleFormData formData)
			throws ProcessingException;

	/**
	 * Deletes all approvals for the assigned file
	 * 
	 * @param fileId
	 *            Long
	 * @throws ProcessingException
	 */
	public void deleteRolesForFile(Long fileId) throws ProcessingException;

	/**
	 * Fetches the role id for the assigned role name
	 * 
	 * @param name
	 *            String
	 * @return Long
	 * @throws ProcessingException
	 */
	public Long getRoleId(String name) throws ProcessingException;

	/**
	 * Returns the admin role id
	 * 
	 * @return Long
	 * @throws ProcessingException
	 */
	public Long getAdminRoleId() throws ProcessingException;

	/**
	 * Returns the user role id
	 * 
	 * @return Long
	 * @throws ProcessingException
	 */
	public Long getUserRoleId() throws ProcessingException;
}
