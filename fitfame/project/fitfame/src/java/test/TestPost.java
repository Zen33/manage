package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import fitfame.common.util.FileUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class TestPost {

	private static String CompleteInfoUrl = "http://112.124.52.165:8080/fitfame/v1/restapi/activity/topic/add?";
	private static String BindFamilyUrl = "http://112.124.52.165:8080/Shaddock/v1/uplord?";
	private static String MessageUrl = "http://localhost/Shaddock/v1/message?";
	private static String ShareUrl = "http://localhost:8080/Shaddock/v1/share?";
	private static String UpLordSportsUrl = "http://localhost:8080/Shaddock/v1/sports?";
	
	public static void main(String [] args) throws Exception
	{
		System.out.println("return:"+httpCompleteInfo(CompleteInfoUrl));
	}

	private static String httpCompleteInfo(String url) throws Exception {
		URL u = null;
		HttpURLConnection con = null;
		//String data = "mail=fd@qq.com&sex=female&name=zyn&nickName=th&picType=jpg&vp=1.0_shaddock&uid=10917573&";
		String data = "token=E49C82F3EB39678DAA35ED1B2B38B11C&sex=male&picType1=jpg&username=害虫&content=sf斯蒂芬&";
		//String data = "readerId=20000000&senderId=12728328&mType=voice&mFormate=mp3&senderIdentity=app&vp=1.0_shaddock&";
		//String data = "readerId=20000000&senderId=12728328&sType=pic&mFormate=jpg&senderIdentity=app&vp=1.0_shaddock&message=ok&";
		byte[] image = SaveFileAsString();
		int leng = image.length;
		System.out.println(leng);
		//String imageCode = URLEncoder.encode(image,"utf-8");
		BASE64Encoder encoder = new BASE64Encoder();
		String imageCode = encoder.encode(image);
		//System.out.println(imageCode);
		System.out.println(imageCode.length());
		data += ("pic1=" + imageCode);
//		String imageDecode = URLDecoder.decode(imageCode, "utf-8");
//		System.out.println(imageDecode);
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = decoder.decodeBuffer(imageCode);
		//FileUtil.SaveMessageStringAsMedia(123, b, "jpg");
		//data = URLEncoder.encode(data);
		System.out.println("send_url:"+url);
		System.out.println("send_data:"+data);
		
		//尝试发送请求

		try {

		u = new URL(url);

		con = (HttpURLConnection) u.openConnection();

		con.setRequestMethod("POST");

		con.setDoOutput(true);

		con.setDoInput(true);

		con.setUseCaches(false);

		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		con.setRequestProperty("charset","utf-8");

		OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");

		osw.write(data);

		osw.flush();

		osw.close();

		} catch (Exception e) {

		e.printStackTrace();

		} finally {

		if (con != null) {

		con.disconnect();

		}

		}

		 

		//读取返回内容

		StringBuffer buffer = new StringBuffer();

		try {

		BufferedReader br = new BufferedReader(new InputStreamReader(con

		.getInputStream(), "UTF-8"));

		String temp;

		while ((temp = br.readLine()) != null) {

		buffer.append(temp);

		buffer.append("\n");

		}

		} catch (Exception e) {

		e.printStackTrace();

		}		 

		return buffer.toString();

	}
	
	private static byte[] SaveFileAsString() throws Exception
	{
        File file = new File("C:\\Users\\zyn\\Desktop\\img-512080827-0002.jpg");
        byte[] b = getByte(file);
        //复制一份，坚持函数是否可还原
        //FileUtil.SaveMessageStringAsMedia(123, new String(b,"ISO-8859-1"), "jpg");
        //return new String(b,"ISO-8859-1");
        return b;
	}
	
	public static byte[] getByte(File file) throws Exception
    {
        byte[] bytes = null;
        if(file!=null)
        {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if(length>Integer.MAX_VALUE)   //当文件的长度超过了int的最大值
            {
                System.out.println("this file is max ");
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while(offset<bytes.length&&(numRead=is.read(bytes,offset,bytes.length-offset))>=0)
            {
                offset+=numRead;
            }
            //如果得到的字节长度和file实际的长度不一致就可能出错了
            if(offset<bytes.length)
            {
                System.out.println("file length is error");
                return null;
            }
            is.close();
        }
        return bytes;
    }
}
