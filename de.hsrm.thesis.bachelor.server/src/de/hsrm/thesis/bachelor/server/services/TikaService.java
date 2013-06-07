package de.hsrm.thesis.bachelor.server.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.hsrm.thesis.bachelor.shared.services.ITikaService;

public class TikaService extends AbstractService implements ITikaService {

  @Override
  public void extractDataFromFile(File file) throws ProcessingException {
    Parser parser = new AutoDetectParser();

    try {
      InputStream stream = new FileInputStream(file);
      DefaultHandler handler = new DefaultHandler();
      Metadata metadata = new Metadata();

      //FIXME
      ParseContext context = new ParseContext();
      parser.parse(stream, handler, metadata, context);

      stream.close();

      for (String key : metadata.names()) {
        System.out.println(key + "     ---      " + metadata.get(key));
      }

    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (SAXException e) {
      e.printStackTrace();
    }
    catch (TikaException e) {
      e.printStackTrace();
    }

  }
}
