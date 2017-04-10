package org.hni.security.utils;

import java.security.SecureRandom;
import java.util.Base64;

import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;



public class HNISecurityUtils {

	public static String getHash(String authCode, Object salt) {
		ByteSource slt = new SimpleByteSource(org.apache.commons.codec.binary.Base64.decodeBase64((String) salt));
		Hash h = new SimpleHash("SHA-256", authCode, slt, 1024);
		Base64.Encoder enc = Base64.getEncoder();
		return new String(enc.encode(h.getBytes()));
	}

	public static String getSalt() {
		byte[] salt = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		Base64.Encoder enc = Base64.getEncoder();
		return enc.encodeToString(salt);
	}
}
