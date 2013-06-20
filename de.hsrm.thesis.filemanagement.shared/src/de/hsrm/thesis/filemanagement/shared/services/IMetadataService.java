package de.hsrm.thesis.filemanagement.shared.services;

import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import de.hsrm.thesis.filemanagement.shared.services.formdata.MetadataFormData;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface IMetadataService extends IService2 {

	MetadataFormData create(MetadataFormData formData) throws ProcessingException;

	void delete(Long[] ids) throws ProcessingException;

	MetadataFormData update(MetadataFormData formData) throws ProcessingException;

	public Object[][] getAttributes(Long filetypeId) throws ProcessingException;

	public void addMetadata(Long attributeId, Long fileId, String metadata) throws ProcessingException;

	public Object[][] getMetadata(Long fileId) throws ProcessingException;

	Object[][] getMetadataAttributeMatrix(Long fileId, Long filetypeId, boolean onlyAttributesForFiletyp)
			throws ProcessingException;

	public Long getAttributeId(String attributeName) throws ProcessingException;

	public Object getMetadataValue(String attributeName, Long fileId) throws ProcessingException;

	public Map<String, Object> getMetdataMapForFile(Long fileId, Long filetypeId) throws ProcessingException;

	public void updateOrInsertMetadata(Long attributeId, Long fileId, String metadata) throws ProcessingException;

	public Object[][] getDetailedMetadataForAllFileTypes() throws ProcessingException;
	
	public Object[][] getMetadataForFiles(Long[] fileIds, String... attributeNames) throws ProcessingException;
}
