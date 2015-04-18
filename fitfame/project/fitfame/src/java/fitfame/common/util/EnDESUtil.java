package fitfame.common.util;

import java.security.Key;

import javax.crypto.Cipher;

public class EnDESUtil {

	private static String strDefaultKey = "p5w.net";
	private Cipher encryptCipher = null;
	//private Cipher decryptCipher = null;

	/**
	* 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	* hexStr2ByteArr(String strIn) 互为可逆的转换过程
	*
	* @param arrB
	* 需要转换的byte数组
	* @return 转换后的字符串
	* @throws Exception
	* 本方法不处理任何异常，所有异常全部抛出
	*/
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
		int intTmp = arrB[i];
		// 把负数转换为正数
		while (intTmp < 0) {
		intTmp = intTmp + 256;
		}
		// 小于0F的数需要在前面补0
		if (intTmp < 16) {
		sb.append("0");
		}
		sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	* 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	* 互为可逆的转换过程
	*
	* @param strIn
	* 需要转换的字符串
	* @return 转换后的byte数组
	* @throws Exception
	* 本方法不处理任何异常，所有异常全部抛出
	* @author Administrator
	*/
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
	
		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
		String strTmp = new String(arrB, i, 2);
		arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	* 默认构造方法，使用默认密钥
	*
	* @throws Exception
	*/
	public EnDESUtil() throws Exception {
		this(strDefaultKey);
	}

	/**
	* 指定密钥构造方法
	*
	* @param strKey 指定的密钥
	*
	* @throws Exception
	*/
	public EnDESUtil(String strKeyA) throws Exception {
		//Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key keyA = getKey(strKeyA.getBytes());
		
		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, keyA);
	
		//decryptCipher = Cipher.getInstance("DES");
		//decryptCipher.init(Cipher.DECRYPT_MODE, keyA);
	}

	/**
	* 加密字节数组
	*
	* @param arrB 需加密的字节数组
	*
	* @return 加密后的字节数组
	* @throws Exception
	*/
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	* 加密字符串
	*
	* @param strIn 需加密的字符串
	*
	* @return 加密后的字符串
	* @throws Exception
	*/
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	/**
	* 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	*
	* @param arrBTmp 构成该字符串的字节数组
	*
	* @return 生成的密钥
	* @throws java.lang.Exception
	*/
	private Key getKey(byte[] arrBTmp) throws Exception {
	// 创建一个空的8位字节数组（默认值为0）
	byte[] arrB = new byte[8]; //8个字节也就是64位（DES算法是密匙是56位，对64位的数据块进行加密和解密）
	/*
	3DES（即Triple DES）是DES向AES过渡的加密算法，它使用3条64位的密钥对数据进行三次加密。是DES的一
	3DES3DES
	个更安全的变形。它以DES为基本模块，通过组合分组方法设计出分组加密算法。比起最初的DES，3DES更为安全。
	3DES-算法介绍
	设Ek()和Dk()代表DES算法的加密和解密过程，K代表DES算法使用的密钥，P代表明文，C代表密表，这样，
	3DES加密过程为：C=Ek3(Dk2(Ek1(P)))
	3DES解密过程为：P=Dk1((EK2(Dk3(C)))
	*/
	//另外一种是3-DES加密，这种加密是用三个密钥进行加密解密的。假设密钥为k1,k2,k3.这样一来密钥的长度就可变成168位了（3*56），如果K1=K3的话密钥长度就是112位了（2*56）
	//加密的过程为：1.用k1对明文进行加密，得到Str1。2.用k2对Str1进行解密得到Str2。3.用K3对Str2进行加密得到最终的密文Str3、
	//解密过程为：1.用K3对密文进行解密得到Str2。2.再用K2对Str2进行加密得到Str1。3.用k1对Str1进行解密就可以得到最终的明文了。

	// 将原始字节数组转换为8位
	for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
		arrB[i] = arrBTmp[i];
	}

	// 生成密钥
	Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

	return key;
	}
}
