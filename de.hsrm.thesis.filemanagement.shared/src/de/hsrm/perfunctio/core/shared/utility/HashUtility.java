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
package de.hsrm.perfunctio.core.shared.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @see <a
 *      href="https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo/blob/d95a1816cc0d362fffa23da7fdab2626962e8467/bahbah/org.eclipse.scout.bahbah.server/src/org/eclipse/scout/bahbah/server/util/HashUtility.java">https://github.com/BSI-Business-Systems-Integration-AG/org.eclipsescout.demo</a>
 * @author BSI Business Systems Integration AG
 * 
 */
public class HashUtility {

	private final static int NUM_CYCLES = 1000;

	/**
	 * creates a new random salt (64bits) according to the SHA1PRNG algorithm.
	 * 
	 * @return 8byte with the random salt.
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] createSalt() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[8]; // 64 bits
		random.nextBytes(salt);
		return salt;
	}

	/**
	 * creates a SHA-256 hash using the given data input and salt.
	 * 
	 * @param data
	 *            the data to hash
	 * @param salt
	 *            the salt to use
	 * @return the hash
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] hash(byte[] data, byte[] salt)
			throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt);
		byte[] key = digest.digest(data);
		for (int i = 0; i < NUM_CYCLES; i++) {
			digest.reset();
			key = digest.digest(key);
		}
		return key;
	}
}
