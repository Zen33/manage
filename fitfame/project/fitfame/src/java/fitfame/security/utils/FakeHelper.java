package fitfame.security.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.util.CharsetUtil;

import sun.misc.BASE64Decoder;

public class FakeHelper {

	static String url = "http://localhost:80";
	static String username = "username";
	static String passwd = "abcd1234";
	static String originContent = "hello test";

	public static HttpRequest register() {

		String p = "";
		try {
			p = RSAUtils.encryptByPublicKey(passwd,
					RSAUtils.loadPublicKey(RSAUtils.PUBLIC_KEY));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder s = new StringBuilder();
		s.append(url);
		s.append("?m=reg&");
		s.append(String.format(ContanstsValue.REGISTER_REQUEST, username, p));
		try {
			s.append(String.format("&aes=%s", AesUtils.encrypt(originContent)));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.append("&");
		HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,
				HttpMethod.GET, s.toString());
		request.setHeader(HttpHeaders.Names.HOST, "localhost");
		request.setHeader(HttpHeaders.Names.CONNECTION,
				HttpHeaders.Values.CLOSE);
		request.setHeader(HttpHeaders.Names.ACCEPT_ENCODING,
				HttpHeaders.Values.GZIP);

		return request;
	}

	public static void serverHandleRequest(HttpRequest request) {
		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(
				request.getUri());
		Map<String, List<String>> params = queryStringDecoder.getParameters();
		if (!params.isEmpty()) {
			for (Entry<String, List<String>> p : params.entrySet()) {
				String key = p.getKey();
				if (key.equals("m")) {
					String method = p.getValue().get(0);
					if (method.compareToIgnoreCase("reg") >= 0) {
						serverHandlerReg(request);
					} else if (method.compareToIgnoreCase("logon") >= 0) {
						serverHandleLogon(request);
					}
				}

			}

		}
	}

	public static void serverHandlerReg(HttpRequest request) {
		String username = "";
		String passwd = "";
		String aesEncrypted = "";
		String content = request.getContent().toString(CharsetUtil.UTF_8);
		if (content == null || content.length() == 0) {

			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(
					request.getUri());
			Map<String, List<String>> params = queryStringDecoder
					.getParameters();

			for (Entry<String, List<String>> p : params.entrySet()) {
				String key = p.getKey();
				if (key.compareToIgnoreCase("username") >= 0) {
					username = p.getValue().get(0);
				}
				if (key.compareToIgnoreCase("passwd") >= 0) {
					
					passwd = p.getValue().get(0);
				}
				if(key.compareToIgnoreCase("aes") >=0){
					aesEncrypted = p.getValue().get(0);
				}
			}

			System.out.println("username = " + username);
			System.out.println("passwd = " + passwd);
			System.out.println("aes = " + aesEncrypted);

			try {
				String result = RSAUtils.decryptByPrivateKey(passwd,
						RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY));
				System.out.println("decrypted result :" + result);
			} catch (Exception e) {
				System.out.println("decryt failed");
			}
			
			try {
				String aesDecrypted = AesUtils.decrypt(aesEncrypted);
				System.out.println("Aes decrypted :" + aesDecrypted);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			String[] values = content.split("&");
//			assert values.length == 2;
			passwd = values[1];
			passwd = passwd.substring("passwd=".length());
			
			try {
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] buffer = decoder.decodeBuffer(passwd);
				
				String result = RSAUtils.decryptByPrivateKey(RSAUtils.bytesToHexString(buffer),
						RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY));
				System.out.println("decrypted result :" + result);
			} catch (Exception e) {
				System.out.println("decryt failed");
			}
			
			aesEncrypted = values[2];
			aesEncrypted = aesEncrypted.substring("aes=".length());
			
			try {
				aesEncrypted = AesUtils.decrypt(aesEncrypted, "1234567890abcdef");
				System.out.println("aes :"+ aesEncrypted);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void serverHandleLogon(HttpRequest request) {

	}

}
