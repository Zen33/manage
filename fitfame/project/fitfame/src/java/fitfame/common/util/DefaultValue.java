/**
 * 
 */
package fitfame.common.util;

/**
 * 
 *
 */
public class DefaultValue {
	public static final int defaultVersion = 1;
	
	//帐号长度限制
	public static final int telLength = 11;
	
	//密码长度限制
	public static final int maxPwLength = 32;
	
	//密码最小长度
	public static final int minPwLength = 6;
	
	//邮箱长度限制
	public static final int maxMailLength = 32;
	
	//昵称最长长度限制
	public static final int maxusernameLength = 10;
	
	//uid长度
	public static final int uidLength = 8;	
	
	//uid头id
	public static final int defaultUserHeadId = 1;
	
	//订单
	public static final int NopayOrder = 1;
	public static final int PaidOrder = 2;
	public static final int RedOrder = 3;

	public static final long defaultOrderTime = 30 * 60 * 1000;

	public static final String WeiPay = "weizhifu";
	
	public static final String ZhiFuBao = "zhifubao";
	
	public static final int SaleType_Pre = 2;
	
	public static final int SaleType_Now = 1;
	
	public static final int SaleType_Out = 3;
	
	public static final long defaultExpectDay = 1;

	public static final long defaultInterval = 20 * 60 * 1000;
	
	public static final int VerifyCountLimit = 3;
	
	public static final int FindPwLimit = 3;
	
	public static final int DetailRecommend = 1;
	
	public static final int DetailGoods = 2;
	
	public static final int ContentType_Words = 1;
	
	public static final int ContentType_Pic = 2;
	
	public static final int maxTopicLength = 100;
}
