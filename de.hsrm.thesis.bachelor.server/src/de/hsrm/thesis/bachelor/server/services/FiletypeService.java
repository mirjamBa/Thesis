package de.hsrm.thesis.bachelor.server.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.xmlparser.ScoutXmlDocument;
import org.eclipse.scout.commons.xmlparser.ScoutXmlDocument.ScoutXmlElement;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.osgi.framework.FrameworkUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import de.hsrm.thesis.bachelor.server.filetype.Filetype;
import de.hsrm.thesis.bachelor.server.filetype.FiletypeContainer;
import de.hsrm.thesis.bachelor.server.filetype.Language;
import de.hsrm.thesis.bachelor.shared.services.IFiletypeService;

public class FiletypeService extends AbstractService implements IFiletypeService {
  private FiletypeContainer container;

  @Override
  public void organizeFiletypes() throws ProcessingException {
    URL filePath = FrameworkUtil.getBundle(getClass()).getResource("resources/xml/types.xml");
    try {
      File file = new File(FileLocator.toFileURL(filePath).getFile());

      if (updateNecessary(file)) {
        FileInputStream stream = new FileInputStream(file);
        ScoutXmlDocument doc = new ScoutXmlDocument(stream);
        ScoutXmlElement filetypecontainer = doc.getRoot();
        ScoutXmlElement types = filetypecontainer.getChild("types");

        clearFiletypes();

        container = new FiletypeContainer();
        for (int i = 0; i < types.countChildren(); i++) {
          ScoutXmlElement ft = types.getChild(i);

          Filetype f = new Filetype();
          f.setId(ft.getAttributeAsInt("id"));
          f.setName(ft.getChild("name").getText());
          if (ft.hasChild("language")) {
            f.setLanguage(solveLanguage(ft.getChild("language").getText()));
          }
          f.setVersionControl(ft.getChild("versionControl").getTextAsBoolean());
          container.addFiletype(f);
          insertFiletype(f);
        }

        stream.close();

      }
    }
    catch (IOException e) {
      throw new ProcessingException(e.getMessage());
    }
  }

  private boolean updateNecessary(File f) throws ProcessingException {
    long lastModified = f.lastModified();
    if (lastModified > getLastInsertTimestamp()) {
      return true;
    }
    return false;
  }

  private void setLastInsertTimestamp() throws ProcessingException {
    URL filePath = FrameworkUtil.getBundle(getClass()).getResource("resources/xml/config.prop");
    FileWriter writer;
    try {
      File file = new File(FileLocator.toFileURL(filePath).getFile());
      writer = new FileWriter(file);

      Date date = new Date();
      writer.write(((Long) date.getTime()).toString());
      writer.flush();
      writer.close();
    }
    catch (IOException e) {
      throw new ProcessingException(e.getMessage());
    }
  }

  private Long getLastInsertTimestamp() throws ProcessingException {
    URL filePath = FrameworkUtil.getBundle(getClass()).getResource("resources/xml/config.prop");
    File file;
    Long l;
    try {
      file = new File(FileLocator.toFileURL(filePath).getFile());
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String date = reader.readLine();
      l = Long.parseLong(date.trim());
      reader.close();
    }
    catch (IOException e) {
      throw new ProcessingException(e.getMessage());
    }

    return l;
  }

  private Language solveLanguage(String lang) {
    for (Language l : Language.values()) {
      if (l.getLanguageCode().equalsIgnoreCase(lang)) {
        return l;
      }
    }
    return null;
  }

  @Override
  public void initFiletypeXML() throws ProcessingException {
    XStream xstream = getXStreamInstance();

    FiletypeContainer c = fillContainer();

    String xml = xstream.toXML(c);

    URL filePath = FrameworkUtil.getBundle(getClass()).getResource("resources/xml/types.xml");
    FileWriter writer;
    try {
      File file = new File(FileLocator.toFileURL(filePath).getFile());
      writer = new FileWriter(file);
      writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
      writer.write(xml);
      writer.flush();
      writer.close();
    }
    catch (IOException e) {
      throw new ProcessingException(e.getMessage());
    }
  }

  private void clearFiletypes() throws ProcessingException {
    SQL.delete("DELETE FROM filetype");
  }

  private void insertFiletype(Filetype filetype) throws ProcessingException {
    SQL.insert("INSERT INTO filetype (name, language, version_control) VALUES(:name, :language, :version_control)",
        new NVPair("name", filetype.getName()),
        new NVPair("language", (filetype.getLanguage() != null) ? filetype.getLanguage().getLanguageCode() : null),
        new NVPair("version_control", filetype.isVersionControl()));
    setLastInsertTimestamp();
  }

  private FiletypeContainer fillContainer() {
    FiletypeContainer c = new FiletypeContainer();
    List<Filetype> list = new ArrayList<Filetype>();

    Filetype t1 = new Filetype();
    t1.setId(0);
    t1.setName("Standard");
    t1.setVersionControl(false);

    Filetype t2 = new Filetype();
    t2.setId(1);
    t2.setName("Dokument");
    t2.setVersionControl(true);
    t2.setLanguage(Language.DE);

    Filetype t3 = new Filetype();
    t3.setId(2);
    t3.setName("Bild");
    t3.setVersionControl(false);

    list.add(t1);
    list.add(t2);
    list.add(t3);

    c.setTypes(list);

    return c;

  }

  private XStream getXStreamInstance() {
    XStream xstream = new XStream(new DomDriver("UTF-8"));
    xstream.alias("filetypecontainer", de.hsrm.thesis.bachelor.server.filetype.FiletypeContainer.class);
    xstream.alias("filetype", de.hsrm.thesis.bachelor.server.filetype.Filetype.class);
    xstream.alias("language", de.hsrm.thesis.bachelor.server.filetype.Language.class);
    xstream.useAttributeFor(de.hsrm.thesis.bachelor.server.filetype.Filetype.class, "id");
    return xstream;
  }

}
