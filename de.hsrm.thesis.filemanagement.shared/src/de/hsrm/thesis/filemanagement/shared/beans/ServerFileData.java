/**
 * 
 */
package de.hsrm.thesis.filemanagement.shared.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.Icon;

public class ServerFileData implements Serializable {

	private static final long serialVersionUID = 1L;
	private String m_path;
	private Long m_number;
	private String m_filesize;
	private Icon m_icon;
	private String m_oldName;
	private Date m_lastModified;

	/**
	 * @return the m_lastModified
	 */
	public Date getLastModified() {
		return m_lastModified;
	}

	/**
	 * @param m_lastModified the m_lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.m_lastModified = lastModified;
	}

	public void setFilesize(long bytes) {
		double kilobytes = (bytes / 1024.0);
		BigDecimal b = new BigDecimal(String.valueOf(kilobytes)).setScale(2, BigDecimal.ROUND_HALF_UP);
		m_filesize = b + " kb";
	}

	/**
	 * @return the m_oldName
	 */
	public String getOldName() {
		return m_oldName;
	}

	/**
	 * @param m_oldName the m_oldName to set
	 */
	public void setOldName(String oldName) {
		this.m_oldName = oldName;
	}

	public String getFileExtension() {
		return m_path.split("\\.")[m_path.split("\\.").length - 1];
	}

	/**
	 * @param m_path
	 * @param m_number
	 * @param m_title
	 * @param m_filesize
	 * @param m_icon
	 */
	public ServerFileData(String m_path, Long m_number, long bytes, Icon m_icon) {
		this.m_path = m_path;
		this.m_number = m_number;
		this.m_icon = m_icon;
		setFilesize(bytes);
	}

	/**
	 * @return the m_icon
	 */
	public Icon getM_icon() {
		return m_icon;
	}

	/**
	 * @param m_icon
	 *            the m_icon to set
	 */
	public void setM_icon(Icon m_icon) {
		this.m_icon = m_icon;
	}

	public String getFileformat() {
		String[] parts = m_path.split("\\.");
		return parts[parts.length - 1];
	}

	/**
	 * @return the filesize
	 */
	public String getFilesize() {
		return m_filesize;
	}

	/**
	 * @param filesize
	 *            the filesize to set
	 */
	public void setFilesize(String filesize) {
		m_filesize = filesize;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return m_path;
	}

	/**
	 * @param path
	 *            the path to set
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
	 *            the number to set
	 */
	public void setNumber(Long number) {
		m_number = number;
	}

}
