/**
 * 二次加密的DES
 * 
 */
package fitfame.common.util;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EnDESXUtil {

	protected static Log logger = LogFactory.getLog(EnDESXUtil.class);
	private String strKeyA = "";//第一次加密的密钥
	private static String strDefaultKey = "y3d4k5";	//校验码
	private static char defaultKeyAChar ='c';  //第一次密钥不够位时自动填充的字符
	private static int defaultKeyALen =6;  //第一次密钥的强制长度，必须和解密时定义的一样。
	private EnDESUtil desUtilA=null;

	/**
	* 默认构造方法，使用默认密钥
	* @strKeyA 第一次加密的密钥
	* @throws Exception
	*/
	public EnDESXUtil(String keyA) throws Exception {
		this(keyA,keyA);
	}

	/**
	* 密钥构造方法
	* @param KeyA 第一次加/解密的密钥(必须是defaultKeyALen位，少于defaultKeyALen位在后面补c，多于defaultKeyALen位将被截掉)
	* @param KeyB 校验码
	* @throws Exception
	*/
	private EnDESXUtil(String keyA,String keyB) throws Exception {
		strKeyA = formatStr(keyA,defaultKeyALen,defaultKeyAChar);
		strDefaultKey = keyB;
		desUtilA=new EnDESUtil(strKeyA);
	}

	/**
	* 加密字符串
	*
	* @param strIn 需加密的字符串
	*
	* @return 二次加密后的字符串
	* @throws Exception
	*/
	public String encrypt(String strIn) throws Exception {	
		//logger.info("要加密的字符窜:"+strIn);
		return strDefaultKey+desUtilA.encrypt(strDefaultKey+strIn);
	}

	/**
	 * 将输入的inStr格式化成formatLen长度的字符串
	 * @param inStr
	 * @param formatLen
	 * @param c 长度不够时填充的字符
	 * @return
	 */
	private String formatStr(String inStr,int formatLen, char c){
		if(inStr.length()==formatLen)
		{return inStr;}
		else if(inStr.length()>5)
		{return inStr.substring(0, formatLen);}
		else
		{			
			char[] array = new char[formatLen];
	        System.arraycopy(inStr.toCharArray(), 0, array, 0, inStr.length());
	        Arrays.fill(array, inStr.length(), formatLen, c);
	        return new String(array);
		}
	}
}
