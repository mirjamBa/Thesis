/*******************************************************************************
 * Copyright (c) 2010 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package de.hsrm.perfunctio.server.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.http.servletfilter.HttpServletEx;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.services.IUserProcessService;

/**
 * @author BSI Business Systems Integrations AG, BahBahChat
 * @see <a
 *      href="https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo/blob/d95a1816cc0d362fffa23da7fdab2626962e8467/bahbah/org.eclipse.scout.bahbah.server/src/org/eclipse/scout/bahbah/server/servlet/BahBahAuthenticationServlet.java">http://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo</a>
 */
public class BahBahAuthenticationServlet extends HttpServletEx {
	private static final long serialVersionUID = 1L;
	private static IScoutLogger logger = ScoutLogManager
			.getLogger(BahBahAuthenticationServlet.class);

	/**
	 * max length of username or password to be accepted.
	 */
	public static final int MAX_LENGTH = 32;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		res.setHeader("Pragma", "no-cache"); // HTTP 1.0
		res.setDateHeader("Expires", 0); // prevents caching at the proxy server
		try {
			String user = req.getHeader("User");
			String pass = req.getHeader("Pass");
			if (SERVICES.getService(IUserProcessService.class).isValidUser(
					user, pass)) {
				// OK
				return;
			} else {
				fail(req, res);
				return;
			}
		} catch (Throwable t) {
			logger.error(t.toString());
			fail(req, res);
			return;
		}
	}

	private void fail(HttpServletRequest req, HttpServletResponse res) {
		res.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}
}
