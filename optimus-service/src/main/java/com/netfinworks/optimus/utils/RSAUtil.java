package com.netfinworks.optimus.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import com.netfinworks.util.RSA;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。 需要bcprov-jdk16-140.jar包。
 * js依赖http://www.ohdave.com/rsa/
 */
public class RSAUtil {
	private static final String pubKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1gr+rIfYlaNUNLiFsK/Knb54nQrCRTRMOdujTwkpqKLo4pNYj2StmryWETeFxOCFtCt/7ixTUrU2RGGjkIOlYC3144h0dJKDtPXw9+mFyW1VwWvtfoiSUeKTEbz1tSHghEcdEvVq6qlSQukiLAEZabiwfEE30TQ6g979X6YXhnQIDAQAB";
	private static final String priKeyStr = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBALWCv6sh9iVo1Q0uIWwr8qdvnidCsJFNEw526NPCSmooujik1iPZK2avJYRN4XE4IW0K3/uLFNStTZEYaOQg6VgLfXjiHR0koO09fD36YXJbVXBa+1+iJJR4pMRvPW1IeCERx0S9WrqqVJC6SIsARlpuLB8QTfRNDqD3v1fpheGdAgMBAAECgYEArmr1w3zfCxOxpvitJUUV589aKl/rS7TEmyGomdQZrel1CPlczRXinsmvQ3OTLzjA5geNNCpx2eyunL7YDF+T2WgcvpPlM+TUrBnTI9gKYwLXiWPuWou8oZolHaTuQQuLfnJTZ+6cYjHA3S4xnrJaEvvs8xgy6/UAJWXUpm/dQAECQQDlW9LPOrWaaVFuZqftVTwhTjhVmBZ/AeuXcmmmF2KBGaujbXJuVHVE5rzW9/6TQoWinuK4J/UOLcFl3VTTEPgZAkEAypggJwgYq3uz0KX8YScHV/gUZa56l5gRofMrIpiek4uvt2JM1kqgIq9T9PIOnv7dz7buRk2GXi3GLgEuhDr2JQJBAJPAypZ7QMBfdnkDosyOqzTdegcR+fQJ3aZrq0m3KNr4GY0nlZ8jw4QGjMKDcjmVkhdH+dAe1Ywzx7ICmoF6HgkCQQCTo+lKiIvx7GROWahi5J5lbVTwBQcyEpBHBX8Z5z8pJ1MWwXxdbmTk4gC9MOmW1QWwqg9bDIQvfgw+2n2bv5xBAkEAhUaiDfOywrIB3VUZWTqeIlVfqXHd8a9AcellTgIjK8WIu9gNGIfkWUVoVeOq0GCFTVqwO5tlac+oeDHCCimLyQ==";

	/**
	 * 解密js加密的rsa
	 * 
	 * @param str
	 *            js加密后的字符串;
	 *            js加密参考http://blog.csdn.net/songxiaobing/article/details
	 *            /17505237
	 * @return
	 */
	public static String decryptJSRsa(String str) {
		PrivateKey privateKey;
		try {
			privateKey = RSA.getPrivateKey(priKeyStr);
			Cipher cipher = Cipher.getInstance("RSA/NONE/NoPadding",
					new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			StringBuilder sb = new StringBuilder(new String(cipher.doFinal(Hex
					.decode(str))));
			return sb.reverse().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 原生加解密代码
	 * 
	 * @throws Exception
	 */
	public static void rsaTest() throws Exception {
		KeyFactory factory = KeyFactory.getInstance("RSA",
				new BouncyCastleProvider());

		byte[] bytes = Base64.getDecoder().decode(pubKeyStr);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
		RSAPublicKey publicKey = (RSAPublicKey) factory.generatePublic(spec);
		System.out.println(publicKey.getPublicExponent().toString(16));
		System.out.println(publicKey.getModulus().toString(16));

		byte[] pirvateBytes = Base64.getDecoder().decode(priKeyStr);
		PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(pirvateBytes);
		PrivateKey privateKey = factory.generatePrivate(privateSpec);

		byte[] encode = RSA.encrypt("wch".getBytes(), privateKey);
		System.out.println(Base64.getEncoder().encodeToString(encode));
		System.out.println(new String(RSA.decrypt(encode, publicKey)));

		byte[] publicEncode = RSA.encrypt("pubic".getBytes(), publicKey);
		System.out.println(Base64.getEncoder().encodeToString(publicEncode));
		System.out.println(new String(RSA.decrypt(publicEncode, privateKey)));

		byte[] sign = RSA.sign("wch".getBytes(), privateKey);
		System.out.println(Base64.getEncoder().encodeToString(sign));

		System.out.println(RSA.verify("wch".getBytes(), sign, publicKey));
	}

	public static void main(String[] args) throws Exception {
		rsaTest();
	}

}
