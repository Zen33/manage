package fitfame.common.util;

import fitfame.common.exception.BaseServiceException;
import cn.emay.sdk.client.api.Client;

public class EMayUtil {
	
	private static String key = "0SDK-EBB-0130-JISMP";

	private static String pw = "266627";

	private static Client client;
	
	private static Client getInstance()
	{
		if(client == null)
		{
			synchronized(EMayUtil.class)
			{
				if(client == null)
				{
					try {
						client = new Client(key, pw);
					} catch (Exception e) {
						throw new BaseServiceException("验证类初始失败！", "");
					}
				}
			}
		}
		return client;
	}
	
	public static void SendMessage(String tel, String check) {
		getInstance();
		try {
			int a=client.sendSMS(new String[] {tel}, check, 3);
			System.out.println("短信发送结果:"+a);
		} catch (Exception e) {
			throw new BaseServiceException("短信发送失败！", "");
		}
	}
}
