package de.hsrm.perfunctio.core.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

@InputValidation(IValidationStrategy.PROCESS.class)
	public interface IPermissionControlService extends IService{

	public boolean check(Long currentUser, Object resource, String function) throws ProcessingException;
}
