package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.ITagService;
import de.hsrm.thesis.bachelor.shared.TagFormData;

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

}
