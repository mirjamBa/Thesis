package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

/**
 * Service Interface for image handling
 * @author Mirjam Bayatloo
 *
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IImageProcessService extends IService {

	/**
	 * Returns the content of the image which belongs to the assigned id
	 * 
	 * @param imageId
	 *            String
	 * @return byte[]
	 * @throws ProcessingException
	 */
	public byte[] getImage(String imageId) throws ProcessingException;

	/**
	 * Scales the image-content
	 * 
	 * @param content byte[]
	 * @param height int
	 * @return byte[]
	 */
	public byte[] scaleImage(byte[] content, int height);
}
