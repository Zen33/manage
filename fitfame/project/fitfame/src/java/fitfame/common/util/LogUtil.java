package fitfame.common.util;

import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LogUtil {
	/**
	 * log级别
	 */
	public static final int LogTestLevel = 1;
	public static final int LogRunLevel = 2;
	public static final int LogCurrentLevel = LogRunLevel;

	private static void createExceptionFileWriter(String content) throws Exception 
	{
		// "D:\\Server\\workSpace\\five-dynasty\\src\\java\\net\\red2\\dynasty\\action\\ShopData.java"  /tmp/red2log/re2game.log
		 FileWriter  file = new FileWriter ("/tmp/red2log/re2game_exception.log",true);
		 file.write(content);
		 file.close();
	}
	
	private static void createProcessFileWriter(String content) throws Exception 
	{
		// "D:\\Server\\workSpace\\five-dynasty\\src\\java\\net\\red2\\dynasty\\action\\ShopData.java"  /tmp/red2log/re2game_process.log
		 FileWriter  file = new FileWriter ("/tmp/red2log/re2game_process.log",true);
		 file.write(content);
		 file.close();
	}
	
	private static void createLastProcessFileWriter(String content) throws Exception 
	{
		// "D:\\web_workspace\\five-dynasty\\src\\java\\net\\red2\\dynasty\\action\\ShopData.java"  /tmp/red2log/re2game_last_process.log
		 FileWriter  file = new FileWriter ("/tmp/re2game_last_process.log",true);
		 file.write(content);
		 file.close();
	}
	
	public static void WriteLastProcessLog(String message)
	{
		if(LogRunLevel < LogCurrentLevel)
			return;
		try {
			createLastProcessFileWriter(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void WriteProcessLog(String message, String identity)
	{
		if(LogRunLevel < LogCurrentLevel)
			return;
		String content = "";
		StackTraceElement[] stacks = new Throwable().getStackTrace(); 
		String lineHead = "///////////////////////////////\r\n";
		String lineTail = "//////////////////////////////\r\n";
		content += lineHead;
		content += ("ClassName: " + stacks[1].getClassName() + "\r\n");
		content += ("MethodName: " + stacks[1].getMethodName() + "\r\n");
		content += ("line:" + stacks[1].getLineNumber() + "\r\n");
		content += ("Message: " + message + "\r\n");
        content += ("identity： " + identity + "\r\n");
		content += lineTail;
		System.out.println(content);
		try {
			createProcessFileWriter(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void WriteLog(String message,String identity)
	{
		if(LogRunLevel < LogCurrentLevel)
			return ;
		String content = "";
		StackTraceElement[] stacks = new Throwable().getStackTrace(); 
		String lineHead = "+++++++++++++++++++++++++++++++++++\r\n";
		String lineTail = "-----------------------------------\r\n";
		content += lineHead;
		content += ("ClassName: " + stacks[1].getClassName() + "\r\n");
		content += ("MethodName: " + stacks[1].getMethodName() + "\r\n");
		content += ("line:" + stacks[1].getLineNumber() + "\r\n");
		content += ("Message: " + message + "\r\n");
		Date time = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String timeLog = "在" + year + "年" + month + "月" + day + "日"
        + hour + "时" + minute + "分" + second + "秒\r\n";
        content += timeLog;
        content += ("异常者： " + identity + "\r\n");
		content += lineTail;
		System.out.println(content);
		try {
			createExceptionFileWriter(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void WriteTimeLog()
	{
		if(LogRunLevel < LogCurrentLevel)
			return;
		String content = "";
		StackTraceElement[] stacks = new Throwable().getStackTrace(); 
		String lineHead = "+++++++++++++++++++++++++++++++++++\r\n";
		String lineTail = "-----------------------------------\r\n";
		content += lineHead;
		content += ("ClassName: " + stacks[1].getClassName() + "\r\n");
		content += ("MethodName: " + stacks[1].getMethodName() + "\r\n");
		content += ("line:" + stacks[1].getLineNumber() + "\r\n");
		Date time = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String timeLog = "在" + year + "年" + month + "月" + day + "日"
        + hour + "时" + minute + "分" + second + "秒\r\n";
		content += timeLog ;
		content += "\r\n";
		content += time.getTime();
		content += "\r\n";
		content += lineTail;
		System.out.println(content);
		try {
			createProcessFileWriter(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void PrintfTimeLog(JSONObject json)
	{
		Date date = new Date();
		json.accumulate("timeLog", date.getTime());
	}
}
