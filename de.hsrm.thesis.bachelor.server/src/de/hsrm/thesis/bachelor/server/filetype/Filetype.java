package de.hsrm.thesis.bachelor.server.filetype;

public class Filetype {

  private int id;
  private String name;
  private boolean versionControl;
  private Language language;

  public char getVersionControlChar() {
    return (versionControl == true) ? 't' : 'f';
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the versionControl
   */
  public boolean isVersionControl() {
    return versionControl;
  }

  /**
   * @param versionControl
   *          the versionControl to set
   */
  public void setVersionControl(boolean versionControl) {
    this.versionControl = versionControl;
  }

  /**
   * @return the language
   */
  public Language getLanguage() {
    return language;
  }

  /**
   * @param language
   *          the language to set
   */
  public void setLanguage(Language language) {
    this.language = language;
  }

  @Override
  public String toString() {
    return "Filetype: " + name + "\nID: " + id + "\nVersion_Control: "
        + versionControl + "\nLanguage: " + language;
  }

}
