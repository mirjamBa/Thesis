package de.hsrm.thesis.filemanagement.shared.services.code;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

/**
 * Long-CodeType for all file types
 * @author Mirjam Bayatloo
 *
 */
public class FileTypeCodeType extends AbstractCodeType<Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 5100L;

  public FileTypeCodeType() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("FileType");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(90.0)
  public static class CollectionCode extends AbstractDCMITypeVocabularyCode {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 5009L;
    public static String uri = "http://purl.org/dc/dcmitype/Collection";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Collection");
    }

    @Override
    public String getUri() {
      return uri;
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(80.0)
  public static class DatasetCode extends AbstractDCMITypeVocabularyCode {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 5008L;
    public static String uri = "http://purl.org/dc/dcmitype/Dataset";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Dataset");
    }

    @Override
    public String getUri() {
      return uri;
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(100.0)
  public static class EventCode extends AbstractDCMITypeVocabularyCode {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 5010L;
    public static String uri = "http://purl.org/dc/dcmitype/Event";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Event");
    }

    @Override
    public String getUri() {
      return uri;
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(20.0)
  public static class ImageCode extends AbstractDCMITypeVocabularyCode {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 5002L;
    public static String uri = "http://purl.org/dc/dcmitype/Image";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Image");
    }

    @Override
    public String getUri() {
      return uri;
    }

    @Override
    public Long getId() {
      return ID;
    }

  }

  @Order(60.0)
  public static class InteractiveResourceCode extends AbstractDCMITypeVocabularyCode {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 5006L;
    public static String uri = "http://purl.org/dc/dcmitype/InteractiveResource";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("InteractiveResource");
    }

    @Override
    public String getUri() {
      return uri;
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(30.0)
  public static class MovingImageCode extends AbstractDCMITypeVocabularyCode {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 5003L;
    public static String uri = "http://purl.org/dc/dcmitype/MovingImage";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("MovingImage");
    }

    @Override
    public String getUri() {
      return uri;
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(110.0)
  public static class PhysikalischesObjektCode extends AbstractDCMITypeVocabularyCode {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 5011L;
    public static String uri = "http://purl.org/dc/dcmitype/PhysicalObject";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("PhysicalObject");
    }

    @Override
    public String getUri() {
      return uri;
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(120.0)
  public static class ServiceCode extends AbstractDCMITypeVocabularyCode {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 5012L;
    public static String uri = "http://purl.org/dc/dcmitype/Service";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Service");
    }

    @Override
    public String getUri() {
      return uri;
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(70.0)
  public static class SoftwareCode extends AbstractDCMITypeVocabularyCode {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 5007L;
    public static String uri = "http://purl.org/dc/dcmitype/Software";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Software");
    }

    @Override
    public String getUri() {
      return uri;
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(50.0)
  public static class SoundCode extends AbstractDCMITypeVocabularyCode {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 5005L;
    public static String uri = "http://purl.org/dc/dcmitype/Sound";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Sound");
    }

    @Override
    public String getUri() {
      return uri;
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(40.0)
  public static class StillImageCode extends AbstractDCMITypeVocabularyCode {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 5004L;
    public static String uri = "http://purl.org/dc/dcmitype/StillImage";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("StillImage");
    }

    @Override
    public String getUri() {
      return uri;
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(10.0)
  public static class TextCode extends AbstractDCMITypeVocabularyCode {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 5001L;
    public static String uri = "http://purl.org/dc/dcmitype/Text";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Text");
    }

    @Override
    public String getUri() {
      return uri;
    }

    @Override
    public Long getId() {
      return ID;
    }
  }
}
