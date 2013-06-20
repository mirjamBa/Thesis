/**
 * 
 */
package de.hsrm.thesis.filemanagement.shared.nonFormdataBeans;

import java.io.Serializable;

import javax.swing.Icon;

public class ServerFileData implements Serializable {

	private static final long serialVersionUID = 1L;
	private String m_path;
	private Long m_number;
	private String m_filesize;
	private Icon m_icon;

	public void setFilesize(long bytes) {
		// TODO
		double kilobytes = (bytes / 1024.0);
		// double megabytes = (kilobytes / 1024);
		m_filesize = kilobytes + " kB";
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
