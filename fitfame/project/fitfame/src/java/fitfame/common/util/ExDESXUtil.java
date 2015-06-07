/**
 * 二次解密的DES
 * 
 */
package fitfame.common.util;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExDESXUtil {

	protected static Log logger = LogFactory.getLog(ExDESXUtil.class);
	private static String strDefaultKey = "y3d4k5";	//第一次解密的密钥，必须和加密时定义的一样。
	private static int defaultKeyLen =6;  //第二次密钥的强制长度，必须和加密时定义的一样。
	private ExDESUtil desUtilA=null;
		
	/**
	* 默认构造方法，使用默认密钥
	*
	* @throws Exception
	
	public ExDESXUtil(){
	}
*/
	/**
	* 获取第一次解密后的字符串
	*
	* @param strIn 需解密的字符串	*
	* @return 解密后的字符串(uid)
	* @throws Exception
	*/
	public String decryptA(String strIn){
		if(strIn.length()<defaultKeyLen+13)//至少有defaultKeyBLen+13位
			return null;
		try {
			desUtilA=new ExDESUtil(strIn.substring(0, defaultKeyLen));
			return desUtilA.decrypt(strIn.substring(defaultKeyLen)).substring(defaultKeyLen);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("一次解密出错：",e);
		}
		return null;
	}	
	
	/**
	 * 校验加密字符窜中的校验码
	 * @return 校验通过返回ture，否则返回false
	 */
	public boolean validete(String strIn){
		if(strIn.length()<defaultKeyLen+13)//至少有defaultKeyBLen+13位
			return false;
		try {
			strDefaultKey =  strIn.substring(0, defaultKeyLen);
			desUtilA=new ExDESUtil(strDefaultKey);
			String valideteStr = desUtilA.decrypt(strIn.substring(defaultKeyLen)).substring(0,defaultKeyLen);
			if(strDefaultKey.equals(valideteStr)){
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("校验加密字符窜中的校验码出错：",e);
		}
		return false;
	}
	
	public String decrypt(String encryptedStr) throws Exception{
		String strDefaultKey = encryptedStr.substring(0, defaultKeyLen);
		ExDESUtil desUtilA=new ExDESUtil(strDefaultKey);
		return desUtilA.decrypt(encryptedStr.substring(defaultKeyLen)).substring(defaultKeyLen);
	}
}
