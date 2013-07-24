package de.hsrm.perfunctio.database.derby.server.services;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringHolder;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.server.services.common.pwd.AbstractPasswordManagementService;
import org.eclipse.scout.rt.server.services.common.pwd.IPasswordPolicy;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.SERVICES;
import org.osgi.framework.ServiceRegistration;

import de.hsrm.perfunctio.core.shared.security.ResetPasswordPermission;
import de.hsrm.perfunctio.core.shared.services.IPasswordProcessService;
import de.hsrm.perfunctio.core.shared.services.IUserProcessService;

/**
 * 
 * @author BSI
 *
 */
public class PasswordProcessService extends AbstractPasswordManagementService implements IPasswordProcessService {

  public static final int MIN_PASSWORD_LENGTH = 3;

  @Override
  public void initializeService(@SuppressWarnings("rawtypes") ServiceRegistration registration) {
    setPasswordPolicy(new IPasswordPolicy() {

      @Override
      public String getText() {
        return "";
      }

      @Override
      public void check(String userId, String newPassword, String userName, int historyIndex) throws ProcessingException {
        SERVICES.getService(IPasswordProcessService.class).checkPassword(newPassword);
        SERVICES.getService(IUserProcessService.class).checkUsername(userName);
      }
    });
  }

  @Override
  public Date getPasswordExpirationDate(String userId) {
    // never expire
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DATE, 1);
    return c.getTime();
  }

  @Override
  protected void checkAccess(String userId, String password) throws ProcessingException {
    if (!ACCESS.check(new ResetPasswordPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
  }

  @Override
  protected String getUsernameFor(String userId) throws ProcessingException {
    StringHolder holder = new StringHolder();
    SQL.selectInto("SELECT username FROM TABUSERS WHERE u_id = :uid INTO :uname", new NVPair("uid", userId), new NVPair("uname", holder));
    return holder.getValue();
  }

  @Override
  protected int getHistoryIndexFor(String userId, String password) throws ProcessingException {
    return 0;
  }

  @Override
  protected void resetPasswordInternal(String userId, String newPassword) throws ProcessingException {
    Long u_id = Long.parseLong(userId);
    SERVICES.getService(IUserProcessService.class).resetPassword(u_id, newPassword);
  }

  @Override
  public void checkPassword(String password) throws VetoException {
    if (StringUtility.length(password) < MIN_PASSWORD_LENGTH) {
      throw new VetoException(TEXTS.get("PasswordMinLength", "" + MIN_PASSWORD_LENGTH));
    }
  }
}
