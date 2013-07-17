package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.service.IService;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.commons.exception.ProcessingException;
/**
 * Service Interface for permission handling
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IRolePermissionService extends IService {

	/**
	 * Fetches all permissions which are approved to the roleId
	 * 
	 * @param roleId
	 *            Long
	 * @return Object[][]
	 * @throws ProcessingException
	 */
	public Object[][] getPermissions(Long roleId) throws ProcessingException;

	/**
	 * Deletes all permissions according to the roleId
	 * 
	 * @param roleId
	 *            Long
	 * @throws ProcessingException
	 */
	public void deletePermissions(Long roleId) throws ProcessingException;

	/**
	 * Stores all permissions according to the roleId
	 * 
	 * @param roleId
	 *            Long
	 * @param permissions
	 *            String[]
	 * @throws ProcessingException
	 */
	public void insertPermissions(Long roleId, String[] permissions)
			throws ProcessingException;
}
