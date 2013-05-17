package de.hsrm.thesis.bachelor.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.thesis.bachelor.shared.ChatFormData;
import de.hsrm.thesis.bachelor.shared.CreateChatPermission;
import de.hsrm.thesis.bachelor.shared.IChatService;
import de.hsrm.thesis.bachelor.shared.ReadChatPermission;
import de.hsrm.thesis.bachelor.shared.UpdateChatPermission;

public class ChatService extends AbstractService implements IChatService {

  @Override
  public ChatFormData prepareCreate(ChatFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateChatPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public ChatFormData create(ChatFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateChatPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here.
    return formData;
  }

  @Override
  public ChatFormData load(ChatFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadChatPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }

  @Override
  public ChatFormData store(ChatFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateChatPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //TODO [mba] business logic here
    return formData;
  }
}
