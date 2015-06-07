package fitfame.common.util;

import java.util.Random;

public class RandomStrUtil {
	 //共36个可选字母和数字
	 private static char[] randList={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','j','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};  
	 public static String getRandomStr(int len) {   
		 String srand="";
		 Random random = new Random(); 
		 // 取随机产生的认证码(n位数字) 
		 for (int i=0;i<len;i++){    
			 srand+=String.valueOf(randList[random.nextInt(36)]);
		 }
		return srand;		 
	 }
}
