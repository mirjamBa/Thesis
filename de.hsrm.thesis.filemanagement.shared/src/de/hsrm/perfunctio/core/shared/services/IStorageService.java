package de.hsrm.perfunctio.core.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;
/**
 * Service Interface for initialization of storage
 * 
 * @author Mirjam Bayatloo
 * 
 */
@InputValidation(IValidationStrategy.PROCESS.class)
public interface IStorageService extends IService2 {

	public static final String META_ENTRYDATE = "EntryDate";
	public static final String META_FILESIZE = "Filsize";
	public static final String META_FILEPATH = "Filepath";
	public static final String META_FILEEXTENSION = "FileExtension";
	public static final String META_NUMBER = "Number";

	/**
	 * Creates a new storage for all application data
	 * 
	 * @throws ProcessingException
	 */
	public void installStorage() throws ProcessingException;

}
