package de.hsrm.thesis.bachelor.server.services;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.services.ILogoProcessService;

public class LogoProcessService extends AbstractService implements ILogoProcessService {

  @Override
  public Object[][] getLogoTableData() throws ProcessingException {
    return new Object[][]{
        new Object[]{"Company 1", "company1"},
        new Object[]{"Company 2", "company2"},
        new Object[]{"Company 3", "company3"},
    };
  }

  @Override
  public byte[] getLogo(String imageId) throws ProcessingException {
    try {
      // TODO get content of image
//      String filename = "c:/" + imageId + ".jpg";
      return IOUtility.getContent(imageId);
    }
    catch (ProcessingException e) {
      // nop
    }
    return null;
  }
}
