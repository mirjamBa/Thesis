/**
 * 
 */
package de.hsrm.thesis.bachelor.server.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.scout.commons.Base64Utility;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.commons.security.SimplePrincipal;
import org.eclipse.scout.http.servletfilter.FilterConfigInjection;
import org.eclipse.scout.http.servletfilter.security.AbstractChainableSecurityFilter;
import org.eclipse.scout.http.servletfilter.security.PrincipalHolder;

/**
 * LDAPSecurityFilter The following properties can be set in the config.ini file:
 * <fully qualified name of class>#active=true/false might be set in the extension point
 * <fully qualified name of class>#realm=abcde required
 * <fully qualified name of class>#failover=true/false default false
 * <fully qualified name of class>#ldapDirectory=[e.g. ldap://100.100.29.4:389] required
 * <fully qualified name of class>#ldapBaseDN=[e.g. dc=bridgesolutions,dc=net] required
 * <fully qualified name of class>#ldapSearchFilter=[e.g. (uid=%s)] required
 * <fully qualified name of class>#ldapSearchBindPassword=[e.g. changeme]
 * <fully qualified name of class>#m_searchBindDN=[e.g. cn=readonly,dc=bridgesolutions,dc=net]
 * based on org.eclipse.scout.http.servletfilter.security.LDAPSecurityFilter
 */

public class LDAPExSecurityFilter extends AbstractChainableSecurityFilter {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(LDAPExSecurityFilter.class);
  public static final String PROP_BASIC_ATTEMPT = "LDAPExSecurityFilter.basicAttempt";

  private String m_directory;
  private String m_baseDn;
  private String m_searchFilter;
  private String m_searchBindPassword;
  private String m_searchBindDN;

  @Override
  public void init(FilterConfig config0) throws ServletException {
    super.init(config0);
    FilterConfigInjection.FilterConfig config = new FilterConfigInjection(config0, getClass()).getAnyConfig();
    m_directory = getParam(config, "ldapDirectory", false);
    m_baseDn = getParam(config, "ldapBaseDN", false);
    m_searchFilter = getParam(config, "ldapSearchFilter", false);
    m_searchBindPassword = getParam(config, "ldapSearchBindPassword", true);
    m_searchBindDN = getParam(config, "ldapSearchBindDN", true);
  }

  protected String getParam(FilterConfig filterConfig, String paramName, boolean nullAllowed) throws ServletException {
    String paramValue = filterConfig.getInitParameter(paramName);
    boolean exists = false;
    if (paramValue == null && nullAllowed) { // check if parameter exists
      Enumeration initParameterNames = filterConfig.getInitParameterNames();
      while (initParameterNames.hasMoreElements() && exists == false) {
        String object = (String) initParameterNames.nextElement();
        exists = object.equals(paramName);
      }
    }
    if (paramValue == null && !exists) {
      throw new ServletException("Missing init-param with name '" + paramName + "'.");
    }
    return paramValue;
  }

  @Override
  protected int negotiate(HttpServletRequest req, HttpServletResponse resp, PrincipalHolder holder) throws IOException, ServletException {
    String h = req.getHeader("Authorization");
    if (h != null && h.matches("Basic .*")) {
      String[] a = new String(Base64Utility.decode(h.substring(6)), "ISO-8859-1").split(":", 2);
      String user = a[0].toLowerCase();
      String pass = a[1];
      if (user != null && pass != null) {
        if (login(user, pass, m_directory, m_baseDn, m_searchBindDN, m_searchBindPassword, m_searchFilter)) {
          // success
          holder.setPrincipal(new SimplePrincipal(user));
          return STATUS_CONTINUE_WITH_PRINCIPAL;
        }
      }
    }
    int attempts = getBasicAttempt(req);
    if (attempts > 2) {
      return STATUS_CONTINUE_CHAIN;
    }
    else {
      setBasicAttept(req, attempts + 1);
      resp.setHeader("WWW-Authenticate", "Basic realm=\"" + getRealm() + "\"");
      return STATUS_CONTINUE_CHAIN;
    }
  }

  private int getBasicAttempt(HttpServletRequest req) {
    int basicAtttempt = 0;
    Object attribute = req.getSession().getAttribute(PROP_BASIC_ATTEMPT);
    if (attribute instanceof Integer) {
      basicAtttempt = ((Integer) attribute).intValue();
    }
    return basicAtttempt;
  }

  private void setBasicAttept(HttpServletRequest req, int attempts) {
    req.getSession().setAttribute(PROP_BASIC_ATTEMPT, attempts);
  }

  @SuppressWarnings("unchecked")
  protected boolean login(String username, String userPassword, String directory, String baseDN, String searchBindDN, String searchBindPassword, String searchFilter) throws ServletException {
    boolean isAuthenticated = false;

    Hashtable env = new Hashtable();
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, directory);
    env.put(Context.SECURITY_PRINCIPAL, searchBindDN);
    env.put(Context.SECURITY_CREDENTIALS, searchBindPassword);

    try {
      DirContext ctx = new InitialDirContext(env);
      SearchControls constraints = new SearchControls();
      constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
      String search = String.format(searchFilter, username);

      NamingEnumeration<SearchResult> results = ctx.search(baseDN, search, constraints);

      SearchResult sr = null;

      if (results != null && results.hasMore()) {
        // Step 2 - simple authentication
        sr = results.next();

        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        if (sr.isRelative()) {
          env.put(Context.SECURITY_PRINCIPAL, sr.getName() + "," + baseDN);
        }
        else {
          env.put(Context.SECURITY_PRINCIPAL, sr.getName());
        }
        env.put(Context.SECURITY_CREDENTIALS, userPassword);

        try {
          new InitialDirContext(env);
          isAuthenticated = true;
        }
        catch (NamingException ne) {
          LOG.warn("User '" + username + "' could not be authenticated");
          isAuthenticated = false;
        }
      }
      else {
        LOG.warn("User '" + username + "' does not exist on LDAP");
        isAuthenticated = false;
      }
    }
    catch (NamingException ne) {
      LOG.error("Exception in getting user DN from LDAP: " + ne);
      throw new SecurityException(ne.getMessage(), ne);
    }
    return isAuthenticated;
  }
}
