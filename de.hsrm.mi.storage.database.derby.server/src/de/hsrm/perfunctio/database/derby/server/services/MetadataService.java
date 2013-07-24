package de.hsrm.perfunctio.database.derby.server.services;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.IntegerHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringHolder;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.services.IAttributeService;
import de.hsrm.perfunctio.core.shared.services.IMetadataService;
import de.hsrm.perfunctio.core.shared.services.code.DatatypeCodeType;

public class MetadataService extends AbstractService
		implements
			IMetadataService {

	private boolean existsMetadataForFile(Long attributeId, Long fileId)
			throws ProcessingException {
		IntegerHolder ih = new IntegerHolder();
		SQL.selectInto(
				"SELECT COUNT(attribute_id) FROM metadata WHERE file_id = :fileId AND attribute_id = :attributeId INTO :ih",
				new NVPair("fileId", fileId), new NVPair("attributeId",
						attributeId), new NVPair("ih", ih));
		return ih.getValue() > 0;
	}

	@Override
	public void addMetadata(Long attributeId, Long fileId, String metadata)
			throws ProcessingException {
		SQL.insert(
				"INSERT INTO metadata (file_id, attribute_id, value) VALUES (:fileId, :attrId, :value)",
				new NVPair("fileId", fileId),
				new NVPair("attrId", attributeId),
				new NVPair("value", metadata));
	}

	@Override
	public Object[][] getMetadataAttributeMatrix(Long fileId, Long filetypeId,
			boolean onlyAttributesForFiletyp) throws ProcessingException {

		Object[][] attributes = SERVICES.getService(IAttributeService.class)
				.getAttributes(filetypeId, false);
		Map<Long, String> map = getMetadataMap(getAllMetadataForFile(fileId));
		Object[][] matrix = new Object[attributes.length][];

		for (int i = 0; i < matrix.length; i++) {
			Object value = null;
			if (map.containsKey((Long) attributes[i][0])) {
				value = map.get((Long) attributes[i][0]);
			}
			matrix[i] = new Object[]{attributes[i][0], attributes[i][1], value,
					attributes[i][3]};
		}
		
		return matrix;

	}
	
	private Map<Long, String> getMetadataMap(Object[][] metadata){
		Map<Long, String> map = new HashMap<Long, String>();
		for (int i = 0; i < metadata.length; i++) {
			map.put((Long) metadata[i][0], (String) metadata[i][1]);
		}
		return map;
	}
	
	private Object[][] getAllMetadataForFile(Long fileId) throws ProcessingException{
		Object[][] metadata = SQL
				.select("SELECT attribute_id, value FROM metadata WHERE file_id = :fileId",
						new NVPair("fileId", fileId));
		return metadata;
	}

	@Override
	public Map<String, Object> getMetdataMapForFile(Long fileId, Long filetypeId)
			throws ProcessingException {
		
		Object[][] attributes = SERVICES.getService(IAttributeService.class).getAttributes(filetypeId, true);
		Map<Long, String> map = getMetadataMap(getAllMetadataForFile(fileId));
		
		Map<String, Object> result = new HashMap<String, Object>();

		// attId, name, value, datatype
		for (int i = 0; i < attributes.length; i++) {
			String attributeName = (String) attributes[i][1];
			String value = null;
			if(map.containsKey((Long) attributes[i][0])){
				value = map.get((Long) attributes[i][0]);
			}
			Long datatype = (Long) attributes[i][3];
			result.put(attributeName, DatatypeCodeType.getValue(datatype, value));
		}
		return result;
	}
	@Override
	public Object getMetadataValue(String attributeName, Long fileId)
			throws ProcessingException {
		Object[][] res = SQL
				.select("SELECT attribute_id, datatype FROM metadata_attribute WHERE name = :name",
						new NVPair("name", attributeName));

		Long attributeId = (Long) res[0][0];
		Long datatype = (Long) res[0][1];

		StringHolder value = new StringHolder();
		SQL.selectInto(
				"SELECT value FROM metadata WHERE attribute_id = :attrId AND file_id = :fileId INTO :value",
				new NVPair("attrId", attributeId),
				new NVPair("fileId", fileId), new NVPair("value", value));

		return DatatypeCodeType.getValue(datatype, value.getValue());
	}

	@Override
	public void updateOrInsertMetadata(Long attributeId, Long fileId,
			String metadata) throws ProcessingException {
		if (existsMetadataForFile(attributeId, fileId)) {
			SQL.update(
					"UPDATE metadata SET value = :value WHERE file_id = :fileId AND attribute_id = :attributeId",
					new NVPair("fileId", fileId), new NVPair("attributeId",
							attributeId), new NVPair("value", metadata));
		} else {
			addMetadata(attributeId, fileId, metadata);
		}
	}

	@Override
	public Object[][] getMetadataForFiles(Long[] fileIds,
			String... attributeNames) throws ProcessingException {

		Object[][] result = new Object[fileIds.length][attributeNames.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Object[attributeNames.length];
			for (int j = 0; j < attributeNames.length; j++) {
				result[i][j] = getMetadataValue(attributeNames[j], fileIds[i]);
			}
		}
		return result;
	}
}
