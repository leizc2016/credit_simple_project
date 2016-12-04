package com.pactera.pds.u2.commerce.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class GEODesUtil {

	private final static String DES = "DES";
	private final static String PASSWORD_CRYPT_KEY = "__geocloudcivp__";

	/**
	 * Encrypt
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final String encrypt(String data) throws Exception {
		if (data == null) {
			return null;
		}
		byte[] bt = encrypt(data.getBytes("UTF-8"), PASSWORD_CRYPT_KEY.getBytes());
		String strs = new BASE64Encoder().encode(bt);
		return strs;
	}
	
	/**
	 * Decrypt
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final String decrypt(String data) throws Exception {
		if (data == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buf = decoder.decodeBuffer(data);
		byte[] bt = decrypt(buf, PASSWORD_CRYPT_KEY.getBytes());
		return new String(bt, "UTF-8");
	}
	/**
	 * Encrypt
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		
		DESKeySpec dks = new DESKeySpec(key);
		
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		
		return cipher.doFinal(data);
	}

	/**
	 * Decrypt
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();

		DESKeySpec dks = new DESKeySpec(key);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
}