package de.hsrm.thesis.bachelor.server.services;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.services.ILogoProcessService;

public class LogoProcessService extends AbstractService implements ILogoProcessService {

  @Override
  public Object[][] getLogoTableData() throws ProcessingException {
    return new Object[][]{
        new Object[]{"Company 1", "bsi1"},
        new Object[]{"Company 2", "bsi1"},
        new Object[]{"Company 3", "bsi1"},
    };
  }

  @Override
  public byte[] getLogo(String imageId) throws ProcessingException {
    try {
      // TODO get content of image
      return IOUtility.getContent("c:\\" + imageId + ".png");
    }
    catch (ProcessingException e) {
      // nop
    }
    return null;
  }
}
