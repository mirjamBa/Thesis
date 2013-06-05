package de.hsrm.thesis.bachelor.server.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
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

    XStream xstream = getXStreamInstance();
    URL filePath = FrameworkUtil.getBundle(getClass()).getResource("resources/xml/types.xml");
    try {
      File file = new File(FileLocator.toFileURL(filePath).getFile());

      if (updateNecessary(file)) {
        file = new File(FileLocator.toFileURL(filePath).getFile());
        FiletypeContainer con = (FiletypeContainer) xstream.fromXML(file);

        for (Filetype f : con.getTypes()) {
          insertFiletype(f);
        }

      }
    }
    catch (IOException e) {
      throw new ProcessingException(e.getMessage());
    }
  }

//  @Override
//  public void organizeFiletypes() throws ProcessingException {
//    URL filePath = FrameworkUtil.getBundle(getClass()).getResource("resources/xml/types.xml");
//    try {
//      File file = new File(FileLocator.toFileURL(filePath).getFile());
//
//      if (updateNecessary(file)) {
//        FileInputStream stream = new FileInputStream(file);
//        ScoutXmlDocument doc = new ScoutXmlDocument(stream);
//        ScoutXmlElement filetypecontainer = doc.getRoot();
//        ScoutXmlElement types = filetypecontainer.getChild("types");
//
//        clearFiletypes();
//
//        container = new FiletypeContainer();
//        for (int i = 0; i < types.countChildren(); i++) {
//          ScoutXmlElement ft = types.getChild(i);
//
//          Filetype f = new Filetype();
//          f.setId(ft.getAttributeAsInt("id"));
//          f.setName(ft.getChild("name").getText());
//          if (ft.hasChild("language")) {
//            f.setLanguage(solveLanguage(ft.getChild("language").getText()));
//          }
//          f.setVersionControl(ft.getChild("versionControl").getTextAsBoolean());
//          container.addFiletype(f);
//          insertFiletype(f);
//        }
//
//        stream.close();
//
//      }
//    }
//    catch (IOException e) {
//      throw new ProcessingException(e.getMessage());
//    }
//  }

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
    SQL.insert("INSERT INTO filetype (filetype_id, name, language, version_control) VALUES(:type, :name, :language, :version_control)",
        new NVPair("type", filetype.getId()),
        new NVPair("name", filetype.getName()),
        new NVPair("language", (filetype.getLanguage() != null) ? filetype.getLanguage().getLanguageCode() : null),
        new NVPair("version_control", filetype.isVersionControl()));
    insertMetaattributes(filetype);
    setLastInsertTimestamp();
  }

  private void insertMetaattributes(Filetype filetype) throws ProcessingException {
    long id = filetype.getId();
    if (filetype.getMeta().size() > 0) {
      for (String attribute : filetype.getMeta().keySet()) {
        String datatype = filetype.getMeta().get(attribute);
        SQL.insert("INSERT INTO metadata_attribute (name, datatype_id, filetype_id) VALUES(:name, :datatype_id, :filetype_id)",
            new NVPair("name", attribute),
            new NVPair("datatype_id", datatype),
            new NVPair("filetype_id", id));
      }
    }
  }

  private FiletypeContainer fillContainer() {
    FiletypeContainer c = new FiletypeContainer();
    List<Filetype> list = new ArrayList<Filetype>();

    Filetype t1 = new Filetype();
    t1.setId(1);
    t1.setName("Default");
    t1.setVersionControl(false);

    Map<String, String> meta = new HashMap<String, String>();
    meta.put("Mime-Typ", "java.lang.String");
    meta.put("Titel", "java.lang.String");
    meta.put("Autor", "java.lang.String");
    meta.put("Erfasser", "java.lang.String");
    meta.put("Dateigroesse", "java.lang.String");
    meta.put("Dateiendung", "java.lang.String");
    meta.put("Erstellt am", "java.util.Date");
    meta.put("Geaendert am", "java.util.Date");
    t1.setMeta(meta);

    Filetype t2 = new Filetype();
    t2.setId(2);
    t2.setName("Text");
    t2.setVersionControl(true);
    t2.setLanguage(Language.DE);

    Filetype t3 = new Filetype();
    t3.setId(3);
    t3.setName("Image");
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

  /* (non-Javadoc)
   * @see de.hsrm.thesis.bachelor.shared.services.IFiletypeService#getFiletypes()
   */
  @Override
  public Object[][] getFiletypes() throws ProcessingException {
    return SQL.select("SELECT filetype_id, name FROM filetype");
  }

}
