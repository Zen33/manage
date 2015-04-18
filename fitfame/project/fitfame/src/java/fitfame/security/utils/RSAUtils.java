package fitfame.security.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;

public class RSAUtils {

	//openssl pkcs8 -topk8 -inform PEM -in privatekey.pem -outform PEM -nocrypt -out rsa_private_key.pem

	/*
	public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMm7MJfKb6qYAW9e"
			+ "\r"
			+ "vB42XtXaEVwbJGIO8R10v9xrmmr9cp5hSUuTHKnV0Z3TUEowWY9usp5n+EPDoaOB"
			+ "\r"
			+ "PcswN4/rShkXutfeuGNsqcZDrY2AGo9fKUP15X4IAYTNEof4e8+qr34evBUNBl5x"
			+ "\r"
			+ "uUJmSWzazvNUo7rtv5lqi22ccRMHAgMBAAECgYEAszI88Bk7KGbun2ef45aYl46m"
			+ "\r"
			+ "bRMPNqvfMzNKWxqaiP+qmVYCLcf4ZkUxdukHIV/hrq++q0fgGRHmu79KjPu/3dwA"
			+ "\r"
			+ "jKyxYShcKt/HBQGUtuyrmw2xD4WhtLREjGxsLDyROGKl4jKDI4IuwadBQYErd9Ig"
			+ "\r"
			+ "QBY6HJWhXZCZ6Qw7hwECQQDuzhBpNY7vEMrhyxewVq0ki+2TB2dhZcBdZSHWR/TX"
			+ "\r"
			+ "DmjCXIWmWNq39lh8DjfQG9N4kC7uAGzxcHsbhW2szxAXAkEA2EG9IO6xDy/mzJOW"
			+ "\r"
			+ "lKIobxsRsnfsK5a85sMUr4zwCTMxjwLqkpuhCFOV/hkvO5rKnDhs2KT7Gq2GklLk"
			+ "\r"
			+ "/zJ6kQJBAM5zk7iBNwrzB5LhE5+9r35TApcD5ZpMb1vO3Mv19XQe3u55Moo3cw9r"
			+ "\r"
			+ "h9/oItRl2hL3A7t63rR0u9l1JvYpJQ0CQGC9P6KsxC38Cq33U8QXqijRniAM/2wc"
			+ "\r"
			+ "WVKNcAJ79KDY8tF7x31f+zBjW9S4ZWWvf/VUVHSxkNbFS07HoEL8zNECQGKO9TMF"
			+ "\r"
			+ "FtUHRtxtf0XI1sOHMVAdmoZaSV2nVMWv0KwZoyHAaNydueGL1KkZ80/Fs4uFFDHD"
			+ "\r" + "bbbPRlOVLUkE+JA=" + "\r";
*/
	/*
	public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJuzCXym+qmAFvXrweNl7V2hFc"
			+ "\r"
			+ "GyRiDvEddL/ca5pq/XKeYUlLkxyp1dGd01BKMFmPbrKeZ/hDw6GjgT3LMDeP60oZ"
			+ "\r"
			+ "F7rX3rhjbKnGQ62NgBqPXylD9eV+CAGEzRKH+HvPqq9+HrwVDQZecblCZkls2s7z"
			+ "\r" + "VKO67b+ZaottnHETBwIDAQAB" + "\r";
*/
	
	public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANx7WVWH9l6WSzFl"+"\r"+
"rKnOzSX5TWCWu5kPeqeH0+tBClPYMqH8l5GA6Yx9Th/PtrIedJFG81ZHIgMsGRYm"+"\r"+
"vkoiruNaANJoaZv5CkBTzGbyIrHmaN6mnXCX2C5deUONCUJGhB9EJm1NI3QhUiUN"+"\r"+
"wB8CFor+i3z6EX6Jb7zCBKuNU773AgMBAAECgYB2TMjWF6mdfs7BpmhKhGeWpHlc"+"\r"+
"BgSKT7/j/AE9DRzZd/tS5xD8RUsK0oEEmJLSZ3zsJkGSXYBma1bah9c6N/nVi+a7"+"\r"+
"d2/A/cT+E6YU0/nkop6pbHMnNBu5Bc8yndMgIGrAVQJqYOttRSUTcxIUKdgdVgk0"+"\r"+
"vYIb6Bo9U2n7R1HfoQJBAPbNQdSO2esfn/7fB0RWYDUFI/x/YbQ/KtMfHyR6/Mlo"+"\r"+
"4viCxEuigG62C+Yel8ivfjXYJJXDqsoptKrps/0r/U0CQQDksvjuKdjB5aXUtSrW"+"\r"+
"x74ieGePfzUSWB/sBXTGKoTUXDLGVXm/ceFvUunkD1GdFKTz/AoYe2t6ntbdP2Ce"+"\r"+
"/5tTAkB3IOiPw0xo8+D0047ca50QuqR+MrZknMD9G16a60qtpZU5Q1Wg9JVt2y2v"+"\r"+
"LEEzu5H+R5CRQdtkrfDpNtmMcPlZAkB7+466RtNs4pvRQdoUkDrCozW5dIQICuBb"+"\r"+
"auXQnLJgEg+wuBwFrq5BVNjZu3hKSSvX3ZZ3iUlOM5hWX4p5CCdjAkEAtIo8Fd29"+"\r"+
"hZpWqKEefqX9JtrCg5SwQLozRsy6mp5SmBTteHI9Sto7xaRwfP9YdeIGu1TKoY/S"+"\r"+
"fP0jNzsfvJTkXg=="+"\r";
	public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDce1lVh/ZelksxZaypzs0l+U1g"+"\r"+
"lruZD3qnh9PrQQpT2DKh/JeRgOmMfU4fz7ayHnSRRvNWRyIDLBkWJr5KIq7jWgDS"+"\r"+
"aGmb+QpAU8xm8iKx5mjepp1wl9guXXlDjQlCRoQfRCZtTSN0IVIlDcAfAhaK/ot8"+"\r"+
"+hF+iW+8wgSrjVO+9wIDAQAB"+"\r";
	/**
	 * 锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟斤拷锟叫硷拷锟截癸拷钥
	 * 
	 * @param in
	 *            锟斤拷钥锟斤拷锟斤拷锟斤拷
	 * @throws Exception
	 *             锟斤拷锟截癸拷钥时锟斤拷锟斤拷锟斤拷斐�
	 */
	public static void loadPublicKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPublicKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("锟斤拷钥锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟");
		} catch (NullPointerException e) {
			throw new Exception("锟斤拷钥锟斤拷锟斤拷锟斤拷为锟斤拷");
		}
	}

	/**
	 * 锟斤拷锟街凤拷锟叫硷拷锟截癸拷钥
	 * 
	 * @param publicKeyStr
	 *            锟斤拷钥锟斤拷锟斤拷址锟�
	 * @throws Exception
	 *             锟斤拷锟截癸拷钥时锟斤拷锟斤拷锟斤拷斐�
	 */
	public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("锟睫达拷锟姐法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("锟斤拷钥锟角凤拷");
		} catch (IOException e) {
			throw new Exception("锟斤拷钥锟斤拷锟斤拷锟斤拷荻锟饺★拷锟斤拷锟");
		} catch (NullPointerException e) {
			throw new Exception("锟斤拷钥锟斤拷锟轿拷锟");
		}
	}

	public static void loadPrivateKey(InputStream in) throws IOException,
			NoSuchAlgorithmException, InvalidKeySpecException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String readLine = null;
		StringBuilder sb = new StringBuilder();
		while ((readLine = br.readLine()) != null) {
			if (readLine.charAt(0) == '-') {
				continue;
			} else {
				sb.append(readLine);
				sb.append('\r');
			}
		}

		loadPrivateKey(sb.toString());
	}

	public static PrivateKey loadPrivateKey(String privateKeyStr)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
		
		KeyFactory f = KeyFactory.getInstance("RSA");

		return f.generatePrivate(keySpec);
	}

	public static String encryptByPrivateKey(final String content,
			final PrivateKey key) throws IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, key);
		cipher.update(content.getBytes("UTF-8"));
		return bytesToHexString(cipher.doFinal());
	}

	public static String encryptByPublicKey(final String content,
			final PublicKey key) throws BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException, InvalidKeyException,
			IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, key);
		cipher.update(content.getBytes("UTF-8"));
		return bytesToHexString(cipher.doFinal());
	}

	public static String decryptByPrivateKey(final String hexString,
			final PrivateKey key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			UnsupportedEncodingException, IllegalBlockSizeException,
			BadPaddingException {
		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, key);
		cipher.update(hexToBytes(hexString));
		return new String(cipher.doFinal(), "UTF-8");
	}

	public static String decryptByPublicKey(final String hexString,
			final PublicKey key) throws UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, key);
		cipher.update(hexToBytes(hexString));
		return new String(cipher.doFinal(), "UTF-8");
	}

	/****************************************************************************************************/
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////

	public static String signature(String data, final String privateKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			InvalidKeyException, SignatureException,
			UnsupportedEncodingException {
		PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(
				hexToBytes(privateKey));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey key = factory.generatePrivate(pkcs8);
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(key);
		signature.update(data.getBytes("utf-8"));
		byte[] signed = signature.sign();

		// System.out.println("signed:" + bytesToHexString(signed))

		return bytesToHexString(signed);
	}

	public static boolean verify(final String origin, final String signed,
			final String publicKey) throws NoSuchAlgorithmException,
			InvalidKeySpecException, SignatureException, InvalidKeyException,
			UnsupportedEncodingException {
		X509EncodedKeySpec x509 = new X509EncodedKeySpec(hexToBytes(publicKey));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey key = factory.generatePublic(x509);
		byte[] s = hexToBytes(signed);
		Signature check = Signature.getInstance("SHA1withRSA");
		check.initVerify(key);
		check.update(origin.getBytes("utf-8"));
		return check.verify(s);
	}

	public static String encyptByPrivateKey(final String content,
			final String privatekey) throws NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			UnsupportedEncodingException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeyException {
		PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(
				hexToBytes(privatekey));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey key = factory.generatePrivate(pkcs8);
		System.out.println("algorithm:" + key.getAlgorithm());
		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, key);
		cipher.update(content.getBytes("utf-8"));
		return bytesToHexString(cipher.doFinal());
	}

	public static String decyptByPrivateKey(final String encryped,
			final String privatekey) throws NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			UnsupportedEncodingException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeyException {
		PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(
				hexToBytes(privatekey));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey key = factory.generatePrivate(pkcs8);

		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, key);
		cipher.update(hexToBytes(encryped));
		return bytesToHexString(cipher.doFinal());
	}

	public static String encryptByPublicKey(final String content,
			final String publicKey) throws NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		X509EncodedKeySpec x509 = new X509EncodedKeySpec(hexToBytes(publicKey));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey key = factory.generatePublic(x509);

		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, key);
		cipher.update(content.getBytes("utf-8"));
		return bytesToHexString(cipher.doFinal());
	}

	public static String decryptByPublicKey(final String encryped,
			final String publicKey) throws NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		X509EncodedKeySpec x509 = new X509EncodedKeySpec(hexToBytes(publicKey));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey key = factory.generatePublic(x509);

		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, key);
		cipher.update(hexToBytes(encryped));
		return new String(cipher.doFinal());
	}

	public static final String bytesToHexString(byte[] bytes) {
		StringBuffer hexStr = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			hexStr.append(_table[bytes[i] >>> 4 & 0xf]);
			hexStr.append(_table[bytes[i] & 0xf]);
		}

		return hexStr.toString();
	}

	public static final byte[] hexToBytes(String hex) {
		byte[] bytes = new byte[hex.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),
					16);
		}

		return bytes;
	}

	private static final char[] _table = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

}
