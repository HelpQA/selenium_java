package com.util;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PwdEncrypterUtil {

	private static final Log LOG = LogFactory.getLog(PwdEncrypterUtil.class);

	public static String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			byte[] unencyptedStringByte = unencryptedString.getBytes();
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, generateKey());
			byte[] encryptedByte = cipher.doFinal(unencyptedStringByte);
			Base64.Encoder encoder = Base64.getEncoder();
			encryptedString = encoder.encodeToString(encryptedByte);
		} catch (Exception e) {
			LOG.error("Failed in encrypt method.", e);
			throw new RuntimeException("Unable to encrypt the string", e);
		}
		return encryptedString;
	}

	public static String decrypt(String encryptedString) {
		String decryptedString = null;
		try {
			Cipher cipher = Cipher.getInstance("AES");
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] encryptedTextByte = decoder.decode(encryptedString);
			cipher.init(Cipher.DECRYPT_MODE, generateKey());
			byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
			decryptedString = new String(decryptedByte);
		} catch (Exception e) {
			LOG.error("Failed in decrypt method.", e);
			throw new RuntimeException("Unable to decrypt the string", e);
		}
		return decryptedString;
	}

	private static Key generateKey() {
		Key key = null;
		String inputString = "Thisisasecretkey";
		byte[] keyBytes = inputString.getBytes();
		try {
			key = new SecretKeySpec(keyBytes, "AES");
		} catch (Exception e) {
			LOG.error("Failed in generate key method.", e);
			throw new RuntimeException("Failed in generate key method.", e);
		}
		return key;
	}
}
