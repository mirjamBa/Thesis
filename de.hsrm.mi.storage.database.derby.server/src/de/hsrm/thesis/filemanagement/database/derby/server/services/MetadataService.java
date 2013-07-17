package de.hsrm.thesis.filemanagement.database.derby.server.services;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.IntegerHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringHolder;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.filemanagement.shared.services.IMetadataService;
import de.hsrm.thesis.filemanagement.shared.services.code.DatatypeCodeType;

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
	// FIXME auseinander ziehen, macht so keinen sinn, filetype id kann auch
	// anhand des files herausgefunden werden?!
	public Object[][] getMetadataAttributeMatrix(Long fileId, Long filetypeId,
			boolean onlyAttributesForFiletyp) throws ProcessingException {
		String query = "SELECT A.ATTRIBUTE_ID, " + "            A.NAME, "
				+ "            M.VALUE, " + "            A.DATATYPE "
				+ "    FROM    METADATA_ATTRIBUTE A "
				+ "    LEFT OUTER JOIN METADATA M "
				+ "    ON      A.ATTRIBUTE_ID = M.ATTRIBUTE_ID "
				+ "    WHERE   1 = 1 "
				+ "    AND     (M.FILE_ID = :fileId OR M.FILE_ID IS NULL) ";

		if (onlyAttributesForFiletyp) {
			query = query + "    AND     A.FILETYPE_ID = :filetypeId ";
		} else {
			query = query
					+ "    AND    (A.FILETYPE_ID = :filetypeId OR A.FILETYPE_ID IS NULL) ";
		}

		return SQL.select(query, new NVPair("filetypeId", filetypeId),
				new NVPair("fileId", fileId));
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
	public Map<String, Object> getMetdataMapForFile(Long fileId, Long filetypeId)
			throws ProcessingException {

		Object[][] metadata = getMetadataAttributeMatrix(fileId, filetypeId,
				false);
		Map<String, Object> map = new HashMap<String, Object>();

		// attId, name, value, datatype
		for (int i = 0; i < metadata.length; i++) {
			String attributeName = (String) metadata[i][1];
			String value = (String) metadata[i][2];
			Long datatype = (Long) metadata[i][3];
			map.put(attributeName, DatatypeCodeType.getValue(datatype, value));
		}
		return map;
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
