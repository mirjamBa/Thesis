package de.hsrm.thesis.bachelor.server.services;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.services.IImageProcessService;

public class ImageProcessService extends AbstractService implements IImageProcessService {

  @Override
  public byte[] getImage(String imageId) throws ProcessingException {
    try {
      // TODO get content of image
      return IOUtility.getContent(imageId);
    }
    catch (ProcessingException e) {
      // nop
    }
    return null;
  }
}
