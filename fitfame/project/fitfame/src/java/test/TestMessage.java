package test;

import cn.emay.sdk.client.api.Client;

public class TestMessage {

	private static String key = "0SDK-EBB-0130-JISMP";

	private static String pw = "266627";

	private static Client client;

	public static void main(String[] args) throws Exception {
		client = new Client(key, pw);
		TestGetFee();
		testGetBalance();
		//testChargeUp();
		//testSendSMS();
		//testSendSMSWithAdd();
	}

	private static void TestRegister() {
		try {
			int i = client.registEx(pw);
			System.out.println("注册结果:" + i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void TestRegisterCompany() {
		try {
			int i = client.registDetailInfo("英巢互动", "张雅儒", "01064652912",
					"18611660553", "836302049@qq.com", "暂时没有", "望京悠乐汇",
					"063700");
			System.out.println("注册企业信息结果:" + i);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void TestGetFee() {
		try {
			double a = client.getEachFee();
			System.out.println("查询短信单价返回结果:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testGetBalance() {
		try {
			double a = client.getBalance();
			System.out.println("查询余额返回结果:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testChargeUp() {
		try {
			int a = client.chargeUp("23456654646546465", "876459");
			System.out.println("短信充值结果:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testSendSMS() {
		try {
			int a=client.sendSMS(new String[] {"18611660553"}, "【英巢互动】拼乐注册码为123324", 3);
			System.out.println("短信发送结果:"+a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testSendSMSWithAdd(){
		try {
			int a=client.sendSMS(new String[] {"18611660553"}, "验证码是2349876","验证码", 3);
			System.out.println("短信发送结果:"+a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
