package xlo.tools.rsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * @author XiaoLOrange
 * @time 2021.01.19
 * @title
 */

public class Rsa {

	private final String publicKey;
	private final String privateKey;

	public Rsa(){
		this(RsaKey.getKey());
	}

	public Rsa(Map<String, String> key){
		this.publicKey = key.get(RsaKey.PUBLIC_KEY);
		this.privateKey = key.get(RsaKey.PRIVATE_KEY);
	}

	/**
	 * 公钥加密
	 * @param str
	 * @return
	 */
	public String encrypt(String str){
		return encrypt(str, publicKey);
	}

	/**
	 * 私钥解密
	 * @param str
	 * @return
	 */
	public String decrypt(String str){
		return decrypt(str, privateKey);
	}

	/**
	 * 公钥加密
	 *
	 * @param str 需要加密的字符串
	 *
	 * @return outStr 公钥加密后的内容
	 */
	public static String encrypt(String str, String publicKey) {
		String outStr = null;
		try {
			// base64编码的公钥
			byte[] decoded = Base64.decodeBase64(publicKey);
			RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
					.generatePublic(new X509EncodedKeySpec(decoded));
			// RSA加密
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
		} catch (Exception e) {
		}
		return outStr;
	}

	/**
	 * RSA私钥解密
	 *
	 * @param str 加密字符串
	 *
	 * @return 私钥解密后的内容
	 */
	public static String decrypt(String str, String privateKey) {
		String outStr = null;
		try {
			// 64位解码加密后的字符串
			byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
			// base64编码的私钥
			byte[] decoded = Base64.decodeBase64(privateKey);
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
			// RSA解密
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			outStr = new String(cipher.doFinal(inputByte));
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return outStr;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}
}
