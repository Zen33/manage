package fitfame.common.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESedeUtil {
	private static final BASE64Decoder dec = new BASE64Decoder();
	private static final BASE64Encoder enc = new BASE64Encoder();
	private static final String ALGORITHM = "DESede";
	private static final String PADDING_NO = "DESede/ECB/NoPadding";
	private static final String DEFAULT_KEY = "A92F38654F3A250602AF1E69A88699E3";
	
	public static byte[] encrypt(byte[] src, byte[] key)throws Exception {
		SecureRandom sr = new SecureRandom();
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM); 
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(PADDING_NO); 
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr); 

		int src_len = src.length;		
		if ((src_len % 8) != 0){
			int align_len = ((src_len / 8) + 1) * 8;

			//字节数补足8的整数倍
			byte[] src_b = new byte[align_len];
			for (int i=0; i<src_len; i++){
				src_b[i] = src[i];
			}
			//		
			for (int i=src_len; i<align_len; i++){
				src_b[i] = 0;
			}

			return cipher.doFinal(src_b);
		}
		else{
			return cipher.doFinal(src);
		}
	}
	
	/** 
	 * 解密 
	 * @param src 数据源 
	 * @param key 密钥，长度必须是8的倍数 
	 * @return   返回解密后的原始数据 
	 * @throws Exception 
	 */ 
	public static byte[] decrypt(byte[] src, byte[] key)throws Exception {
		SecureRandom sr = new SecureRandom(); 
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM); 
		SecretKey securekey = keyFactory.generateSecret(dks); 
		Cipher cipher = Cipher.getInstance(PADDING_NO); 
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(src); 
	}
	
	/** 
	 * 解密 
	 * @param data 
	 * @param key
	 * @return 
	 * @throws Exception 
	 */ 
	public final static String decrypt(String data, String key){ 
		try { 
			byte[] decodes = decrypt(dec.decodeBuffer(data), dec.decodeBuffer(key));

			int pos = -1;
			for (int i=decodes.length-1; i>-1; i--){
				if (decodes[i] != 0){
					pos = i+1;
					break;
				}
			}
			
			byte[] tmpB = new byte[pos];
			for (int i=0; i<pos; i++){
				tmpB[i] = decodes[i];
			}
		
			return new String(tmpB, "UTF-8");
		}catch(Exception e) { 
		}
		return null; 
	}
	
	/**
	 * 解密（使用默认密钥）
	 * @param data
	 * @return
	 */
	public final static String decrypt(String data){
		return decrypt(data, DEFAULT_KEY);
	}
	
	/** 
	 * 加密 
	 * @param data
	 * @param key
	 * @return encrypted data
	 * @throws Exception 
	 */ 
	public final static String encrypt(String data, String key){
		if ( (null == data) || (null == key) ){
			return null;
		}
		
		if (key.length() < 1){
			return null;
		}
		
		try {
			byte[] encodes = encrypt(data.getBytes("UTF-8"),dec.decodeBuffer(key));
			return enc.encode(encodes);
		}catch(Exception e) { 
		}
		return null; 
	}
	
	/**
	 * 加密（使用默认密钥）
	 * @param data
	 * @return
	 */
	public final static String encrypt(String data){
		return encrypt(data, DEFAULT_KEY);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String userID = "12345678";
		System.out.println("userID:" + userID);
		int num = (int)(Math.random()*9000) + 1000;
		userID = userID + num;
		System.out.println("处理后userID:" + userID);
		
		System.out.println("原文为:"+userID);
		String enSrc = encrypt(userID, DEFAULT_KEY);
		System.out.println("加密后:"+enSrc);
		String deSrc = decrypt(enSrc, DEFAULT_KEY);
		System.out.println("解密后:"+deSrc);
		System.out.println("还原后:"+deSrc.substring(0, 8));
	}

}
