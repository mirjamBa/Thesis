/**
 * 
 */
package de.hsrm.thesis.bachelor.server.util;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.LongHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;

public class TagUtility {
  public static final String TAG_SEPERATOR = ";";

  public static String[] filterTagnames(String newTags) {
    if (newTags.endsWith(TAG_SEPERATOR)) {
      newTags = newTags.substring(0, newTags.length() - 2);
    }
    String[] names = newTags.split(TAG_SEPERATOR);
    for (int i = 0; i < names.length; i++) {
      names[i] = names[i].trim();
    }
    return names;
  }

  public static Long getTagId(String name) throws ProcessingException {
    LongHolder tagId = new LongHolder();
    SQL.selectInto("SELECT tag_id FROM tag WHERE name = :name INTO :tagId",
        new NVPair("name", name),
        new NVPair("tagId", tagId));
    return tagId.getValue();
  }

  public static boolean tagsExisting() throws ProcessingException {
    Object[][] tags = SQL.select("SELECT tag_id, name FROM tag");
    return tags.length > 0;
  }

}
