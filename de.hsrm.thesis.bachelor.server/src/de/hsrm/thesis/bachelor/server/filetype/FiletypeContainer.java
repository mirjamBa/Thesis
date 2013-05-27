package de.hsrm.thesis.bachelor.server.filetype;

import java.util.ArrayList;
import java.util.List;

public class FiletypeContainer {

  private List<Filetype> types;

  public FiletypeContainer() {
    types = new ArrayList<Filetype>();
  }

  public void addFiletype(Filetype filetype) {
    types.add(filetype);
  }

  public List<Filetype> getTypes() {
    return types;
  }

  public void setTypes(List<Filetype> types) {
    this.types = types;
  }

  @Override
  public String toString() {
    return types.toString();
  }

}
