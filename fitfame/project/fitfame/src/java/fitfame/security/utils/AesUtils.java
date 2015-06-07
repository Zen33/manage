package fitfame.security.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AesUtils {

	public static final String key = "1234567890lkdier";

	public static String encrypt(String origin , String key)
			throws UnsupportedEncodingException, GeneralSecurityException,
			NoSuchPaddingException {
		SecretKeySpec sks = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, sks);

		byte[] result = cipher.doFinal(origin.getBytes("UTF-8"));
		BASE64Encoder b = new BASE64Encoder();
		String base64 = b.encode(result);
		return base64;
	}

	public static String decrypt(String en, String key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		// base64 decode
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] c = decoder.decodeBuffer(en);
		SecretKeySpec sks = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, sks);
		byte[] result = cipher.doFinal(c);
		return new String(result, "UTF-8");
	}
	
	public static String encrypt(String origin) throws UnsupportedEncodingException, NoSuchPaddingException, GeneralSecurityException{
		return encrypt(origin, key);
	}
	
	public static String decrypt(String en) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException{
		return decrypt(en, key);
	}

}
