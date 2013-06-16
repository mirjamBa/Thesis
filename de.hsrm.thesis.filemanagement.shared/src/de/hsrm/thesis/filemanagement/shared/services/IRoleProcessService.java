package de.hsrm.thesis.filemanagement.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

import de.hsrm.thesis.filemanagement.shared.formdata.RoleFormData;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IRoleProcessService extends IService {

  public Object[][] getRoles() throws ProcessingException;

  public RoleFormData create(RoleFormData formData) throws ProcessingException;

  public Long[] getApprovedRolesForFile(Long fileId) throws ProcessingException;

  public void deleteRoles(Long[] roleIds) throws ProcessingException;

  public RoleFormData update(RoleFormData formData) throws ProcessingException;

public void deleteRolesForFile(Long fileId) throws ProcessingException;
}
