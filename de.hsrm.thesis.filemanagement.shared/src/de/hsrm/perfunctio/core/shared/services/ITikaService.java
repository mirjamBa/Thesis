package de.hsrm.perfunctio.core.shared.services;

import java.io.File;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

/**
 * Service Interface for tika-tool-handling
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface ITikaService extends IService2 {

	/**
	 * Extracts all meta data from file which have been detected by the
	 * tika-tool. Returns a attribute (key), meta data (value) map.
	 * 
	 * @param file
	 *            File
	 * @return Map<String, String>
	 * @throws ProcessingException
	 */
	public Map<String, String> extractDataFromFile(File file)
			throws ProcessingException;
}
