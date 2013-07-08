package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

import de.hsrm.thesis.filemanagement.shared.services.formdata.AssignToRoleFormData;

@InputValidation(IValidationStrategy.PROCESS.class)
	public interface IAssignToRoleService extends IService{

	public AssignToRoleFormData create(AssignToRoleFormData formData) throws ProcessingException;

	public void remove(Long roleNr, String[] permission) throws ProcessingException;
}
