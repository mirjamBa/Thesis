package de.hsrm.perfunctio.core.shared.beans;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Container for the file data, extracted during the upload process.
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class ServerFileData implements Serializable {

	private static final long serialVersionUID = 1L;
	private String m_path;
	private Long m_number;
	private String m_filesize;
	private String m_oldName;
	private Date m_lastModified;
	private File file;

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the m_lastModified
	 */
	public Date getLastModified() {
		return m_lastModified;
	}

	/**
	 * @param m_lastModified
	 *            the m_lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.m_lastModified = lastModified;
	}
	
	public void setFilesize(long bytes) {
		double kilobytes = (bytes / 1024.0);
		BigDecimal b = new BigDecimal(String.valueOf(kilobytes)).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		m_filesize = b + " kb";
	}

	/**
	 * @return the m_oldName
	 */
	public String getOldName() {
		return m_oldName;
	}

	/**
	 * @param m_oldName
	 *            the m_oldName to set
	 */
	public void setOldName(String oldName) {
		this.m_oldName = oldName;
	}

	/**
	 * @param m_path
	 * @param m_number
	 * @param m_title
	 * @param m_filesize
	 * @param m_icon
	 */
	public ServerFileData(String m_path, Long m_number, long bytes) {
		this.m_path = m_path;
		this.m_number = m_number;
		setFilesize(bytes);
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
