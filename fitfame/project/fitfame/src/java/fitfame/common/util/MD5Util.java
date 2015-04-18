/*
 * copywrite 2011 深圳证券信息有限公司
 * 不能修改和删除上面的版权声明
 * 此代码属于北京研究网编写，在未经允许的情况下不得传播复制
 */
package fitfame.common.util;

import java.security.MessageDigest;

/**
 * @comment:todo MD5加密工具类
 * @date 2011-5-10
 * @author maxin
 * @since 1.0
 */
public class MD5Util {
	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * MD5加密
	 * @param src 源字符串
	 * @param uppercase 是否为大写输出
	 * @return
	 */
	public static String toMD5Str(String src, boolean uppercase) {
		try {
			byte[] strTemp = src.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			if(uppercase){
				return new String(str).toUpperCase();
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * MD5加密（默认大写输出）
	 * @param src 源字符串
	 * @return
	 */
	public static String toMD5Str(String src) {
		return toMD5Str(src, true);
	}

	public static void main(String[] args) {
		System.out.println(toMD5Str("a"));
		System.out.println(toMD5Str("a", false));
	}
}
