package de.hsrm.thesis.bachelor.shared.services.process;

import java.util.Set;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IUserProcessService extends IService {

  static final String PERMISSION_KEY = "permission_id";

  ICode<Integer> getUserPermission(String userName) throws ProcessingException;

  Object[][] getUsers() throws ProcessingException;

  void deleteUser(Long[] u_id) throws ProcessingException;

  void createUser(UserFormData formData) throws ProcessingException;

  void updateUser(UserFormData formData) throws ProcessingException;

  public void registerUser() throws ProcessingException;

  public void unregisterUser() throws ProcessingException;

  public Set<String> getUsersOnline() throws ProcessingException;

  public Long[] getUserRoles(Long userId) throws ProcessingException;

  public long getUserId(String username) throws ProcessingException;
}
