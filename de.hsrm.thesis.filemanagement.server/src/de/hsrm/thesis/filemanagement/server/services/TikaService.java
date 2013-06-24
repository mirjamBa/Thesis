package de.hsrm.thesis.filemanagement.server.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.hsrm.thesis.filemanagement.shared.services.ITikaService;

public class TikaService extends AbstractService implements ITikaService {

	@Override
	public Map<String, String> extractDataFromFile(File file)
			throws ProcessingException {
		Parser parser = new AutoDetectParser();
		Map<String, String> metaValues = new HashMap<String, String>();

		try {
			InputStream stream = new FileInputStream(file);
			DefaultHandler handler = new DefaultHandler();
			Metadata metadata = new Metadata();

			parser.parse(stream, handler, metadata, new ParseContext());

			stream.close();

			for (String key : metadata.names()) {
				metaValues.put(key.toUpperCase(), metadata.get(key));
//				System.out.println(key.toUpperCase() + " ----- " + metadata.get(key.toUpperCase()));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}

		return metaValues;
	}
}
