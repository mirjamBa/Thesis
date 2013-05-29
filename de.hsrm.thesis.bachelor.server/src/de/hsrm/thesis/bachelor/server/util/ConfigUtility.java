/**
 * 
 */
package de.hsrm.thesis.bachelor.server.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.osgi.framework.FrameworkUtil;

/**
 * @author mba
 */
public class ConfigUtility {
  private static ConfigUtility _instance;
  private Map<String, String> m_properties;
  private String SEPERATOR = ":";

  public static synchronized ConfigUtility getInstance() {
    if (_instance == null) {
      _instance = new ConfigUtility();
    }
    return _instance;
  }

  public String getPropertyValue(String key) throws ProcessingException {
    if (m_properties == null) {
      initProperties();
    }

    if (!m_properties.containsKey(key)) {
      throw new ProcessingException("Property-Key " + key + " not available");
    }
    return m_properties.get(key);
  }

  private void initProperties() throws ProcessingException {
    URL filePath = FrameworkUtil.getBundle(getClass()).getResource("resources/xml/config.prop");
    m_properties = new HashMap<String, String>();
    File file;
    try {
      file = new File(FileLocator.toFileURL(filePath).getFile());
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String prop;
      while ((prop = reader.readLine()) != null) {
        m_properties.put(prop.split(SEPERATOR)[0], prop.split(SEPERATOR)[1]);
      }
      reader.close();
    }
    catch (IOException e) {
      throw new ProcessingException(e.getMessage());
    }
  }

}
