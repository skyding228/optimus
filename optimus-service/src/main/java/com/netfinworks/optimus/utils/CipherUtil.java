package com.netfinworks.optimus.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtil {
	public static String deskey = "{PONY}";

	public static String encrypt(String strIn) {

		try {
			Key key = getKey(deskey);

			Cipher encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);

			return byteArr2HexStr(encryptCipher.doFinal(strIn.getBytes()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String dencrypt(String strIn) {

		try {
			Key key = getKey(deskey);
			Cipher encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.DECRYPT_MODE, key);
			return new String(encryptCipher.doFinal(hexStringToBytes(strIn)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Key getKey(String strKey) throws Exception {
		byte[] arrBTmp = strKey.getBytes();
		byte[] arrB = new byte[8];
		for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); ++i) {
			arrB[i] = arrBTmp[i];
		}
		Key key = new SecretKeySpec(arrB, "DES");
		return key;
	}

	private static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; ++i) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp += 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String strIn = "666666";
		System.out.println(encrypt(strIn));
		System.out.println(dencrypt("37b8acd28f207ce2"));
	}

	/**
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src
	 *            byte[] data
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

}
