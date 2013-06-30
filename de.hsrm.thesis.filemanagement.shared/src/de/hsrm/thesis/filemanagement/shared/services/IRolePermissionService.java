package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.service.IService;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.commons.exception.ProcessingException;

@InputValidation(IValidationStrategy.PROCESS.class)
  public interface IRolePermissionService extends IService{

  public Object[][] getPermissions(Long roleId) throws ProcessingException;
}
