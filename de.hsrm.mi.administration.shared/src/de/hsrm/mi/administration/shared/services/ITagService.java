package de.hsrm.mi.administration.shared.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.validate.IValidationStrategy;
import org.eclipse.scout.rt.shared.validate.InputValidation;
import org.eclipse.scout.service.IService2;

import de.hsrm.mi.administration.shared.services.formdata.TagFormData;

@InputValidation(IValidationStrategy.PROCESS.class)
public interface ITagService extends IService2 {

  TagFormData create(TagFormData formData) throws ProcessingException;

  public void create(String... tagnames) throws ProcessingException;

  public Object[][] getTags() throws ProcessingException;

  public void updateTag(TagFormData formData) throws ProcessingException;

  public void deleteTag(Long[] ids) throws ProcessingException;
  
  public Long[] getTagsForFile(Long fileId) throws ProcessingException;

}