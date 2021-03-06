package de.hsrm.perfunctio.database.derby.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.LongArrayHolder;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.perfunctio.core.shared.security.CreateTagPermission;
import de.hsrm.perfunctio.core.shared.security.DeleteTagPermission;
import de.hsrm.perfunctio.core.shared.security.ReadTagPermission;
import de.hsrm.perfunctio.core.shared.security.UpdateTagPermission;
import de.hsrm.perfunctio.core.shared.services.ITagService;
import de.hsrm.perfunctio.core.shared.services.formdata.TagFormData;

public class TagService extends AbstractService implements ITagService {

	@Override
	public TagFormData create(TagFormData formData) throws ProcessingException {
		if (!ACCESS.check(new CreateTagPermission())) {
			throw new VetoException(TEXTS.get("VETOCreateTagPermission"));
		}
		create(formData.getTagName().getValue());
		return formData;
	}

	@Override
	public void create(String... tagnames) throws ProcessingException {
		if (!ACCESS.check(new CreateTagPermission())) {
			throw new VetoException(TEXTS.get("VETOCreateTagPermission"));
		}
		for (String tagname : tagnames) {
			SQL.insert("INSERT INTO tag (name) VALUES (:tagName)", new NVPair(
					"tagName", tagname));
		}
	}

	@Override
	public Object[][] getTags() throws ProcessingException {
		if (!ACCESS.check(new ReadTagPermission())) {
			throw new VetoException(TEXTS.get("VETOReadTagPermission"));
		}
		return SQL.select("SELECT tag_id, name FROM TAG");
	}

	@Override
	public void updateTag(TagFormData formData) throws ProcessingException {
		if (!ACCESS.check(new UpdateTagPermission())) {
			throw new VetoException(TEXTS.get("VETOUpdateTagPermission"));
		}
		SQL.update("UPDATE tag SET name = :tagName WHERE tag_id = :tagId",
				new NVPair("tagName", formData.getTagName()), new NVPair(
						"tagId", formData.getTagId()));
	}

	@Override
	public void deleteTag(Long[] ids) throws ProcessingException {
		if (!ACCESS.check(new DeleteTagPermission())) {
			throw new VetoException(TEXTS.get("VETODeleteTagPermission"));
		}
		SQL.delete("DELETE FROM tag_file WHERE tag_id = :{tagIds}", new NVPair(
				"tagIds", ids));
		SQL.delete("DELETE FROM tag WHERE tag_id = :ids",
				new NVPair("ids", ids));
	}

	@Override
	public Long[] getTagsForFile(Long fileId) throws ProcessingException {
		LongArrayHolder tagIds = new LongArrayHolder();

		SQL.selectInto(
				"SELECT tag_id FROM tag_file WHERE file_id = :fileId INTO :tagIds",
				new NVPair("fileId", fileId), new NVPair("tagIds", tagIds));
		return tagIds.getValue();
	}

	@Override
	public String[] filterTagnames(String newTags) {
		if (newTags.endsWith(TAG_SEPERATOR)) {
			newTags = newTags.substring(0, newTags.length() - 2);
		}
		String[] names = newTags.split(TAG_SEPERATOR);
		for (int i = 0; i < names.length; i++) {
			names[i] = names[i].trim();
		}
		return names;
	}

	@Override
	public Long getTagId(String name) throws ProcessingException {
		LongHolder tagId = new LongHolder();
		SQL.selectInto("SELECT tag_id FROM tag WHERE name = :name INTO :tagId",
				new NVPair("name", name), new NVPair("tagId", tagId));
		return tagId.getValue();
	}

	@Override
	public boolean tagsExisting() throws ProcessingException {
		Object[][] tags = SQL.select("SELECT tag_id, name FROM tag");
		return tags.length > 0;
	}

	@Override
	public void deleteTagsForFile(Long fileId) throws ProcessingException {
		if (!ACCESS.check(new DeleteTagPermission())) {
			throw new VetoException(TEXTS.get("VETODeleteTagPermission"));
		}
		SQL.delete("DELETE FROM tag_file WHERE file_id = :fileId", new NVPair(
				"fileId", fileId));
	}

}
