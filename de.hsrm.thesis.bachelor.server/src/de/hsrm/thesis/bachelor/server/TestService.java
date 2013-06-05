package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.CreateTestPermission;
import de.hsrm.thesis.bachelor.shared.ITestService;
import de.hsrm.thesis.bachelor.shared.ReadTestPermission;
import de.hsrm.thesis.bachelor.shared.TestFormData;
import de.hsrm.thesis.bachelor.shared.UpdateTestPermission;

public class TestService extends AbstractService implements ITestService {

  @Override
  public TestFormData prepareCreate(TestFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateTestPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public TestFormData create(TestFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateTestPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here.
    return formData;
  }

  @Override
  public TestFormData load(TestFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadTestPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public TestFormData store(TestFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateTestPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }
}
