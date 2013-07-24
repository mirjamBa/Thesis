package de.hsrm.perfunctio.core.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;
/**
 * Service Interface for all actions, which belongs to files and folder in equal
 * measure
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IFileAndFolderService extends IService {

	/**
	 * Slides file oder folder of the assigned id from current parent folder to
	 * new parent folder
	 * 
	 * @param slidedFFId
	 *            Long
	 * @param oldFolderId
	 *            Long
	 * @param newFolderId
	 *            Long
	 * @throws ProcessingException
	 */
	public void slideFileOrFolder(Long slidedFFId, Long oldFolderId,
			Long newFolderId) throws ProcessingException;
}
