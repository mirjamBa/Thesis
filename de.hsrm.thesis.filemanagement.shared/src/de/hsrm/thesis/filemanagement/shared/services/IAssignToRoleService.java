package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

import de.hsrm.thesis.filemanagement.shared.services.formdata.AssignToRoleFormData;

/**
 * Service Interface for allocation of permissions to roles
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IAssignToRoleService extends IService {

	/**
	 * Allots permissions to role
	 * 
	 * @param formData
	 *            AssignToRoleFormData
	 * @return AssignToRoleFormData
	 * @throws ProcessingException
	 */
	public AssignToRoleFormData create(AssignToRoleFormData formData)
			throws ProcessingException;

	/**
	 * Removes the assigned permissions from the role
	 * 
	 * @param roleNr
	 *            Long
	 * @param permission
	 *            String[]
	 * @throws ProcessingException
	 */
	public void remove(Long roleNr, String[] permission)
			throws ProcessingException;
}
