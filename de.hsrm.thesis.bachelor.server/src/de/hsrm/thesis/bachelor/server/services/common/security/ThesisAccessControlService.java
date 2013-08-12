/**
 * 
 */
package de.hsrm.thesis.bachelor.server.services.common.security;

import java.security.Permission;

import org.eclipse.scout.commons.annotations.Priority;

import de.hsrm.perfunctio.database.derby.server.services.common.security.AccessControlService;

/**
 * @author Mirjam Bayatloo
 */
@Priority(value = 10)
public class ThesisAccessControlService extends AccessControlService {

  @Override
  protected Permission initPermission(String className) {
    try {
      return (Permission) Class.forName(className).newInstance();
    }
    catch (Exception e) {
      logger.error("cannot find permission " + className + ": "
          + e.getMessage());
      return null;
    }
  }
}
