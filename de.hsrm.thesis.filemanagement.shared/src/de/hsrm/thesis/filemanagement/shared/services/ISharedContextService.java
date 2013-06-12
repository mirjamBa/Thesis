package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.IService;

/**
 * Service holding session globals and properties
 * <p>
 * All variables stored in the shared map are automatically synchronized between server and client session <br>
 * Note that all shared variables need to be serializable for using default http proxy handling
 * <p>
 * All variables stored in the local map are not synchronized
 */
public interface ISharedContextService extends IService {

  public <T> void setSharedContextVariable(String name, Class<T> type, T value) throws ProcessingException;
}
