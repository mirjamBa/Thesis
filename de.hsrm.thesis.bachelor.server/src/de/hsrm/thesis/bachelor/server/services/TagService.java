package de.hsrm.thesis.bachelor.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.LongArrayHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.mi.administration.shared.services.ITagService;
import de.hsrm.mi.administration.shared.services.formdata.TagFormData;

public class TagService extends AbstractService implements ITagService {

  @Override
  public TagFormData create(TagFormData formData) throws ProcessingException {
    create(formData.getTagName().getValue());
    return formData;
  }

  @Override
  public void create(String... tagnames) throws ProcessingException {
    for (String tagname : tagnames) {
      SQL.insert("INSERT INTO tag (name) VALUES (:tagName)",
          new NVPair("tagName", tagname));
    }
  }

  @Override
  public Object[][] getTags() throws ProcessingException {
    return SQL.select("SELECT tag_id, name FROM TAG");
  }

  @Override
  public void updateTag(TagFormData formData) throws ProcessingException {
    SQL.update("UPDATE tag SET name = :tagName WHERE tag_id = :tagId",
        new NVPair("tagName", formData.getTagName()),
        new NVPair("tagId", formData.getTagId()));
  }

  @Override
  public void deleteTag(Long[] ids) throws ProcessingException {
    SQL.delete("DELETE FROM tag WHERE tag_id = :ids", new NVPair("ids", ids));
  }

  @Override
  public Long[] getTagsForFile(Long fileId) throws ProcessingException {
    LongArrayHolder tagIds = new LongArrayHolder();

    SQL.selectInto("SELECT tag_id FROM tag_file WHERE file_id = :fileId INTO :tagIds",
        new NVPair("fileId", fileId),
        new NVPair("tagIds", tagIds));
    return tagIds.getValue();
  }

}