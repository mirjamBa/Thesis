package de.hsrm.thesis.bachelor.server.filetype;

public enum Language {
  DE("de"), EN("en");

  private String languageCode;

  private Language(String languageCode) {
    this.languageCode = languageCode;
  }

  public String getLanguageCode() {
    return languageCode;
  }

}
