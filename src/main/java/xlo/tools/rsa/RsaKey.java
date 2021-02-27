package xlo.tools.rsa;

import sun.misc.BASE64Encoder;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaoLOrange
 * @time 2021.01.18
 * @title
 */

public class RsaKey {

	public static final String KEY_ALGORITHM = "RSA";
	public static final String PUBLIC_KEY = "PublicKey";
	public static final String PRIVATE_KEY = "PrivateKey";

	/**
	 * 返回一个Map,键为PublicKey和PrivateKey
	 * @return
	 */
	public static Map<String, String> getKey(){
		//获得对象 KeyPairGenerator 参数 RSA 1024个字节
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyPairGen.initialize(1024);
		//通过对象 KeyPairGenerator 获取对象KeyPair
		KeyPair keyPair = keyPairGen.generateKeyPair();

		BASE64Encoder be = new BASE64Encoder();
		//通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
		Key publicKey = keyPair.getPublic();
		String puk = be.encodeBuffer(publicKey.getEncoded());
		Key privateKey = keyPair.getPrivate();
		String prk = be.encodeBuffer(privateKey.getEncoded());
		//公私钥对象存入map中
		Map<String, String> keyMap = new HashMap<>(2);
		keyMap.put(PUBLIC_KEY, puk);
		keyMap.put(PRIVATE_KEY, prk);
		return keyMap;
	}

}
