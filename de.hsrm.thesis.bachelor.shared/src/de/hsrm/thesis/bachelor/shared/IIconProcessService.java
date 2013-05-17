package de.hsrm.thesis.bachelor.shared;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

@InputValidation(IValidationStrategy.NO_CHECK.class)
public interface IIconProcessService extends IService2 {

  public byte[] loadIcon(String name) throws ProcessingException;

  public void saveIcon(String name, byte[] icon) throws ProcessingException;
}
