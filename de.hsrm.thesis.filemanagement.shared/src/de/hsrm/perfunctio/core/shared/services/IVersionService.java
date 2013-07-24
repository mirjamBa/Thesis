package de.hsrm.perfunctio.core.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import de.hsrm.perfunctio.core.shared.services.formdata.VersionFormData;
/**
 * Service Interface for version handling
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IVersionService extends IService2 {

	/**
	 * Modifies version control in the storage
	 * 
	 * @param formData
	 *            VersionFormData
	 * @return VersionFormData
	 * @throws ProcessingException
	 */
	public VersionFormData updateFileTypeVersionControl(VersionFormData formData)
			throws ProcessingException;

}
