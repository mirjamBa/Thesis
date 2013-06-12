package de.hsrm.thesis.filemanagement.shared.services.code;

import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;

public class AbstractDCMITypeVocabularyCode extends AbstractCode<Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 5000L;
  private String uri = "http://purl.org/dc/dcmitype";

  public String getUri() {
    return uri;
  }

  @Override
  public Long getId() {
    return ID;
  }
}
