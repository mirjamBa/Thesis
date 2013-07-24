package de.hsrm.perfunctio.core.client.handler;

import java.io.File;
import java.util.Map;

import de.hsrm.perfunctio.core.shared.beans.ServerFileData;
import de.hsrm.perfunctio.core.shared.services.formdata.FileFormData;

public class FileUploadData {
	private File m_file;
	private Long m_fileId;
	
	private ServerFileData m_serverFileData;
	private Map<String, String> m_metaValues;
	
	private Long m_parentFolderId;
	
	private FileFormData m_fileFormData;
	
	private String m_fileFormat;
	private Long m_filetypeNr;
	
	public FileUploadData(){
		
	}
	
	/**
	 * @return the m_file
	 */
	public File getFile() {
		return m_file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.m_file = file;
	}

	/**
	 * @return the m_fileId
	 */
	public Long getFileId() {
		return m_fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(Long fileId) {
		this.m_fileId = fileId;
	}

	/**
	 * @return the m_serverFileData
	 */
	public ServerFileData getServerFileData() {
		return m_serverFileData;
	}

	/**
	 * @param serverFileData the serverFileData to set
	 */
	public void setServerFileData(ServerFileData serverFileData) {
		this.m_serverFileData = serverFileData;
	}

	/**
	 * @return the m_metaValues
	 */
	public Map<String, String> getMetaValues() {
		return m_metaValues;
	}

	/**
	 * @param metaValues the metaValues to set
	 */
	public void setMetaValues(Map<String, String> metaValues) {
		this.m_metaValues = metaValues;
	}

	/**
	 * @return the m_parentFolderId
	 */
	public Long getParentFolderId() {
		return m_parentFolderId;
	}

	/**
	 * @param parentFolderId the parentFolderId to set
	 */
	public void setParentFolderId(Long parentFolderId) {
		this.m_parentFolderId = parentFolderId;
	}

	/**
	 * @return the m_fileFormData
	 */
	public FileFormData getFileFormData() {
		return m_fileFormData;
	}

	/**
	 * @param fileFormData the fileFormData to set
	 */
	public void setFileFormData(FileFormData fileFormData) {
		this.m_fileFormData = fileFormData;
	}

	/**
	 * @return the m_fileFormatForm
	 */
	public String getFileFormat() {
		return m_fileFormat;
	}

	/**
	 * @param fileFormat the file format to set
	 */
	public void setFileFormat(String fileFormat) {
		this.m_fileFormat = fileFormat;
	}

	/**
	 * @return the m_filetypeNr
	 */
	public Long getFiletypeNr() {
		return m_filetypeNr;
	}

	/**
	 * @param filetypeNr the filetypeNr to set
	 */
	public void setFiletypeNr(Long filetypeNr) {
		this.m_filetypeNr = filetypeNr;
	}
	
	
	

}
