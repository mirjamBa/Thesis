package de.hsrm.thesis.filemanagement.server.services;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.filemanagement.shared.services.IImageProcessService;

public class ImageProcessService extends AbstractService implements IImageProcessService {

  @Override
  public byte[] getImage(String imageId) throws ProcessingException {
    try {
      return IOUtility.getContent(imageId);
    }
    catch (ProcessingException e) {
      // nop
    }
    return null;
  }
}
