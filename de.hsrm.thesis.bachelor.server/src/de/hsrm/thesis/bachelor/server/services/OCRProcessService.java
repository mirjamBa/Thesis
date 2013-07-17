package de.hsrm.thesis.bachelor.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.services.IOCRProcessService;

public class OCRProcessService extends AbstractService implements IOCRProcessService {

  @Override
  public void doOCR() throws ProcessingException {

//    URL filePath = FrameworkUtil.getBundle(getClass()).getResource("resources/examples/eurotext.pdf");
//    try {
//      File file = new File(FileLocator.toFileURL(filePath).getFile());
//      System.out.println(file.getAbsolutePath());
//      Tesseract instance = Tesseract.getInstance();
////      instance.setHocr(true);
//
//      String result = instance.doOCR(file);
//      System.out.println(result);
//    }
//    catch (IOException e1) {
//      e1.printStackTrace();
//    }
//    catch (TesseractException e) {
//      System.err.println(e.getMessage());
//    }

  }

}
