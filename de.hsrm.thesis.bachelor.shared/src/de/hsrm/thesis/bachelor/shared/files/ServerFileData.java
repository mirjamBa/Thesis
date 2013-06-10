/**
 * 
 */
package de.hsrm.thesis.bachelor.shared.files;

import java.io.Serializable;

public class ServerFileData implements Serializable {

  private static final long serialVersionUID = 1L;
  private String m_path;
  private Long m_number;
  private String m_title;
  private String m_filesize;

  public void setFilesize(long bytes) {
    //TODO
    double kilobytes = (bytes / 1024.0);
//    double megabytes = (kilobytes / 1024);
    m_filesize = kilobytes + " kB";
  }

  public String getFileformat() {
    String[] parts = m_path.split("\\.");
    return parts[parts.length - 1];
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return m_title;
  }

  /**
   * @param title
   *          the title to set
   */
  public void setTitle(String title) {
    m_title = title;
  }

  /**
   * @return the filesize
   */
  public String getFilesize() {
    return m_filesize;
  }

  /**
   * @param filesize
   *          the filesize to set
   */
  public void setFilesize(String filesize) {
    m_filesize = filesize;
  }

  /**
   * @param path
   * @param number
   */
  public ServerFileData(String path, Long number, long bytes) {
    super();
    m_path = path;
    m_number = number;
    setFilesize(bytes);
  }

  /**
   * @return the path
   */
  public String getPath() {
    return m_path;
  }

  /**
   * @param path
   *          the path to set
   */
  public void setPath(String path) {
    m_path = path;
  }

  /**
   * @return the number
   */
  public Long getNumber() {
    return m_number;
  }

  /**
   * @param number
   *          the number to set
   */
  public void setNumber(Long number) {
    m_number = number;
  }

}
