package de.hsrm.perfunctio.core.shared.security;

import java.security.BasicPermission;

/**
 * Authority to register user
 * 
 * @see <a
 *      href="https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo/blob/d95a1816cc0d362fffa23da7fdab2626962e8467/bahbah/org.eclipse.scout.bahbah.shared/src/org/eclipse/scout/bahbah/shared/security/RegisterUserPermission.java">https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo</a>
 * @author BSI Business Systems Integrations AG
 * 
 */
public class RegisterUserPermission extends BasicPermission {

	private static final long serialVersionUID = 0L;

	public RegisterUserPermission() {
		super(RegisterUserPermission.class.getName());
	}
}
