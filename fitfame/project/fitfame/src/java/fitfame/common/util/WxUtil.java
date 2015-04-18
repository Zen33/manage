package fitfame.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class WxUtil {
	public static final String LOGIN_URL = "http://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN";
	public static String DOWNLOAD_URL = "https://mp.weixin.qq.com/cgi-bin/downloadfile?msgid=#msgId&token=";
	public static final String USERNAME = "mylookee@hotmail.com";
	public static final String PASSWORD = "zaq@1980";
	
	// 目录  
	public static String IMAGE_FOLDER = "/image/";  
	public static String VOICE_FOLDER = "/voice/";  
	public static String OTHER_FOLDER = "/other/"; 

	
	/**
&nbsp; 	 * 
&nbsp; 	 * 功能说明:&nbsp; 获取登陆微信公众平台后的cookie
&nbsp; 	 * @return cookie字符串
&nbsp; 	 * @throws Exception
&nbsp; 	 */
	public static String getCookies() throws Exception {

		URL url = new URL(LOGIN_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);// 允许连接提交信息
		connection.setRequestMethod("POST");// 网页提交方式“GET”、“POST”
		connection.setRequestProperty("Referer", "https://mp.weixin.qq.com/");
		connection.setRequestProperty("User-Agent",
				"Mozilla/4.7 [en] (Win98; I)");
		StringBuffer sb = new StringBuffer();
		sb.append("username=" + USERNAME);
		sb.append("&pwd=" + MD5Util.toMD5Str(PASSWORD));
		sb.append("&imgcode=&f=json");
		OutputStream os = connection.getOutputStream();
		os.write(sb.toString().getBytes());
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String cookieVal = null;
		String response_cookie = "";
		String key=null;
		for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++ ) {
			if (key.equalsIgnoreCase("Set-Cookie")) {
				cookieVal = connection.getHeaderField(i);
				cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
				response_cookie = response_cookie + cookieVal + ";";
			}
		}
		System.out.println("response_cookie==" + response_cookie);
		
		String line = br.readLine();
		while (line != null) {
			// 获取token
			if(line.contains("ErrMsg")){
				DOWNLOAD_URL += line.substring(line.lastIndexOf("=") + 1, line.lastIndexOf("\""));
			}
			line = br.readLine();// 打出登录的网页
		}
		return response_cookie;
	}
	
	/**
&nbsp;	 * 
&nbsp;	 * 功能说明: 下载指定消息ID的文件并保存到百度空间
&nbsp; 	 * @param msgId 消息ID
&nbsp;	 * @param message 消息体
&nbsp; 	 * @param fileType 文件类型
&nbsp; 	 * @return 消息保存到百度空间后的名字
&nbsp; 	 * @throws Exception
&nbsp; 	 */
	public static String download(String msgId,  String fileType) throws Exception{
		String cookies = getCookies();

		String url = DOWNLOAD_URL.replace("#msgId", msgId);
		URL download_url = new URL(url);
		HttpURLConnection download_conn = (HttpURLConnection) download_url
				.openConnection();
		download_conn.setRequestProperty("Cookie", cookies);// 设置服务器送登录后的cookie
		
		String type = download_conn.getContentType();
		// 后缀名称
		String suffix = type.substring(type.lastIndexOf("/") + 1);
		System.out.println("type=" + type + ",suffix=" + suffix);
		//long length = download_conn.getContentLength();
		try {
			InputStream in = download_conn.getInputStream();
			byte [] mp3 = new byte [in.available()];
			in.read(mp3);
			File f=new File("D:\\abc.mp3"); 
			if (f.exists()==false) {  
				 f.createNewFile();//create file if not exist  
				}  
			FileOutputStream fos=new FileOutputStream(f);  

			fos.write(mp3);  

			fos.flush();  
 
			fos.close();


		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String msgId = "5938136090262110287";
		try {
			String file_name = download(msgId, "voice");
			System.out.println("file_name==" + file_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

