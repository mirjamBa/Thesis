package de.hsrm.mi.storage.database.derby.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.filemanagement.shared.services.IUserDefinedAttributesService;
import de.hsrm.thesis.filemanagement.shared.services.formdata.MetadataFormData;

public class UserDefinedAttributesService extends AbstractService
		implements
			IUserDefinedAttributesService {

	@Override
	public MetadataFormData create(MetadataFormData formData)
			throws ProcessingException {
		SQL.insert(
				"INSERT INTO metadata_attribute (name, datatype, filetype_id, show_in_table) VALUES(:name, :datatype, :filetype, :showInFileTable)",
				new NVPair("name", formData.getDescription()), new NVPair(
						"datatype", formData.getDatatype().getValue()),
				new NVPair("filetype", formData.getFileType().getValue()),
				new NVPair("showInFileTable", formData.getShowInFileTable()
						.getValue()));

		return formData;
	}

	@Override
	public void delete(Long[] ids) throws ProcessingException {
		SQL.delete("DELETE FROM metadata WHERE attribute_id = :ids",
				new NVPair("ids", ids));

		SQL.delete("DELETE FROM metadata_attribute WHERE attribute_id = :ids",
				new NVPair("ids", ids));
	}

	@Override
	public MetadataFormData update(MetadataFormData formData)
			throws ProcessingException {
		SQL.update(
				"UPDATE metadata_attribute SET name = :name , show_in_table = :display WHERE attribute_id = :id",
				new NVPair("name", formData.getDescription().getValue()),
				new NVPair("id", formData.getAttributeId()), new NVPair(
						"display", formData.getShowInFileTable().getValue()));
		return formData;
	}

	@Override
	public Object[][] getAttributes(Long filetypeId) throws ProcessingException {
		StringBuilder s = new StringBuilder();
		s.append("SELECT attribute_id, " + "                   name, "
				+ "					  show_in_table, " + "                   datatype, "
				+ "                   filetype_id "
				+ "            FROM metadata_attribute ");

		if (filetypeId != null) {
			s.append("            WHERE filetype_id = :filetypeId ");
		}
		s.append("             ORDER BY attribute_id ");

		if (filetypeId != null) {
			return SQL.select(s.toString(),
					new NVPair("filetypeId", filetypeId));
		} else {
			return SQL.select(s.toString());
		}
	}

	@Override
	public Long getAttributeId(String attributeName) throws ProcessingException {
		LongHolder id = new LongHolder();
		SQL.selectInto(
				"SELECT attribute_id FROM metadata_attribute WHERE UPPER(name) LIKE UPPER(:name) INTO :id",
				new NVPair("id", id), new NVPair("name", attributeName));
		return id.getValue();
	}

	@Override
	public Object[][] getDetailedMetadataForAllFileTypes()
			throws ProcessingException {
		Object[][] res = SQL
				.select("SELECT attribute_id, name, datatype FROM metadata_attribute WHERE filetype_id IS NOT NULL");
		return res;
	}

}
