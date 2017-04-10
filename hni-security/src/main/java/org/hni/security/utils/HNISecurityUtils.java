package org.hni.security.utils;

import java.security.SecureRandom;
import java.util.Base64;

import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;



public class HNISecurityUtils {

	public static String getHash(String authCode, byte[] salt) {
		Hash h = new Sha256Hash(authCode, salt, 1000);
		return h.toHex();
	}

	public static String getSalt() {
		byte[] salt = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		Base64.Encoder enc = Base64.getEncoder();
		return enc.encodeToString(salt);
	}
}
