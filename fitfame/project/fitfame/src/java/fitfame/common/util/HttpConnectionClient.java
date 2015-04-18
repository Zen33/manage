package fitfame.common.util;

import java.net.URL;
import java.net.URLEncoder;
import fitfame.common.exception.NetException;
import fitfame.common.http.KeyValue;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpConnectionClient {
	/**
	 * 链接超时
	 */
	private static int DEFAULT_CONNECTION_TIMEOUT = 1000 * 5;
	/**
	 * 传输超时
	 */
	private static int DEFAULT_SO_TIMEOUT = 1000 * 5;
	/**
	 * 最大连接数
	 */
	private static int DEFAULT_CONNECTIONS_MAX_TOTAL = 200;
	/**
	 * 每host最大连接数
	 */
	private static int DEFAULT_CONNECTIONS_MAX_PERHOST = 50;
	// 初始化用到的同步锁
	private final ReentrantLock lock = new ReentrantLock();

	private MultiThreadedHttpConnectionManager connectionManager = null;
	private HttpClient httpClient = null;

	private int connectionTimeOut = DEFAULT_CONNECTION_TIMEOUT;
	private int soTimeOut = DEFAULT_SO_TIMEOUT;
	private int connectionMaxTotal = DEFAULT_CONNECTIONS_MAX_TOTAL;
	private int connectionMaxPerHost = DEFAULT_CONNECTIONS_MAX_PERHOST;
	private String codeing = "UTF-8";

	private HttpClient getHttpClient() {
		lock.lock();
		try {
			if (connectionManager == null) {
				connectionManager = new MultiThreadedHttpConnectionManager();
				configure();
			}
			if (httpClient == null) {
				httpClient = new HttpClient(connectionManager);
			}

		} finally {
			lock.unlock();
		}
		return httpClient;
	}

	public HttpClient getClient(){
		return this.getHttpClient();
	}
	/**
	 * 配置connectionmanager
	 */
	private void configure() {
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setConnectionTimeout(connectionTimeOut);
		params.setMaxTotalConnections(connectionMaxTotal);
		params.setDefaultMaxConnectionsPerHost(connectionMaxPerHost);
		params.setSoTimeout(soTimeOut);
	}

	/**
	 * 返回http网页内容，使用Get方法提交
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String getContextByGetMethod(String url) {
		HttpClient client = getHttpClient();
		// 设置编码
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				codeing);
		GetMethod gm = new GetMethod(url);
		String result = "";
		try {
			client.executeMethod(gm);

			if (gm.getStatusCode() >= 400) {
				throw new HttpException("GET-"+url+ " Connection Error!return Status :"
						+ gm.getStatusCode());
			}
//			result = new String(gm.getResponseBody(), "gbk");
//			System.out.println("result gbk:        "+result);
			result = new String(gm.getResponseBody(), "utf-8");
//			System.out.println("result utf-8:        "+result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			gm.releaseConnection();
		}
		return result;
	}
	
	/**
	 * http get请求，查询参数由queryParamMap拼成
	 * @param url 仅包含get请求路径的url
	 * @param queryParamMap 存放查询参数的键值对
	 * @return get请求结果
	 */
	public String getContextByGetMethod(String url, Map<String, String> queryParamMap){
		if(queryParamMap==null || queryParamMap.size()==0){
			return getContextByGetMethod(url);
		}
		StringBuilder sb = new StringBuilder();
		String key = null;
		boolean first = true;
		Iterator<String> iterator = queryParamMap.keySet().iterator();
		while(iterator.hasNext()){
			if(first){
				sb.append(url);
				sb.append("?");
				first = false;
			}else{
				sb.append("&");
			}
			key = iterator.next();
			sb.append(key);
			sb.append("=");
			try{
				sb.append(URLEncoder.encode(queryParamMap.get(key), "utf-8"));
			}catch(Exception e){
				sb.append(queryParamMap.get(key));
			}
		}
		return getContextByGetMethod(sb.toString());
	}

	/**
	 * 返回http网页内容，使用Post方法提交
	 * 
	 * @param url
	 *            访问地址
	 * @param param
	 *            参数，键值对列表
	 * @return
	 * @throws Exception
	 */
	public int getContextByPostMethod(String url, NameValuePair[] nvps) throws Exception {
		HttpClient client = getHttpClient();
		// 设置编码
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				codeing);
		PostMethod post = null;
		int result = 0;
		try {
			// 设置提交地址
			URL u = new URL(url);
			client.getHostConfiguration().setHost(u.getHost(),
					u.getPort() == -1 ? u.getDefaultPort() : u.getPort(),
					u.getProtocol());
			post = new PostMethod(u.getPath());

			// 提交数据
			post.setRequestBody(nvps);
			client.executeMethod(post);
			result = post.getStatusCode();
			if (post.getStatusCode() >= 400) {
				throw new HttpException("POST-"+url+" Connection Error!return Status :"
						+ post.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (post != null)
				post.releaseConnection();
		}
		return result;
	}

	/**
	 * 返回http网页内容，使用Post方法提交
	 * 
	 * @param url
	 *            访问地址
	 * @param param
	 *            参数，键值对列表
	 * @return
	 */
	public int getContextByPostMethod(String url, List<KeyValue> params) {
		HttpClient client = getHttpClient();
		// 设置编码
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				codeing);
		PostMethod post = null;
		int result =0;
		try {
			// 设置提交地址
			URL u = new URL(url);
			client.getHostConfiguration().setHost(u.getHost(),
					u.getPort() == -1 ? u.getDefaultPort() : u.getPort(),
					u.getProtocol());
			post = new PostMethod(u.getPath());

			// 拼键值对
			NameValuePair[] nvps = new NameValuePair[params.size()];
			int i = 0;
			for (KeyValue kv : params) {
				nvps[i] = new NameValuePair(kv.getKey(), kv.getValue());
				i++;
			}
			// 提交数据
			post.setRequestBody(nvps);
			client.executeMethod(post);
			
			result=post.getStatusCode();
			if (post.getStatusCode() >= 400) {
				throw new HttpException("POST-"+url+" Connection Error!return Status :"
						+ post.getStatusCode());
			}
		} catch (Exception e) {
			throw new NetException("HttpClient catch!", e);
		} finally {
			if (post != null)
				post.releaseConnection();
		}
		return result;
	}

	/**
	 * POST方法提交请求，json字符串最为requestBody
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public int getContextByJosnPost(String url, String json) {
		HttpClient client = getHttpClient();
		PostMethod post = null;
		int result = 0;
		try {
			// 设置提交地址
			URL u = new URL(url);
			client.getHostConfiguration().setHost(u.getHost(),
					u.getPort() == -1 ? u.getDefaultPort() : u.getPort(),
					u.getProtocol());
			post = new PostMethod(u.getPath());

			// 提交数据
			RequestEntity entity = new StringRequestEntity(json,
					MediaType.APPLICATION_JSON, codeing);
			post.setRequestEntity(entity);

			client.executeMethod(post);
			result=post.getStatusCode();
			if (post.getStatusCode() >= 400) {
				throw new HttpException("POST-"+url+" Connection Error!return Status :"
						+ post.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null)
				post.releaseConnection();
		}
		return result;
	}

	/**
	 * 执行DELETE方法
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public int doDelete(String url) {
		HttpClient client = getHttpClient();
		// 设置编码
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				codeing);
		DeleteMethod dm = new DeleteMethod(url);
		int result = 0;
		try {
			client.executeMethod(dm);
			result = dm.getStatusCode();
			if (dm.getStatusCode() >= 400) {
				throw new HttpException("DELETE-"+url+" Connection Error!return Status :"
						+ dm.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dm.releaseConnection();
		}
		return result;
	}

	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public int getConnectionMaxTotal() {
		return connectionMaxTotal;
	}

	public void setConnectionMaxTotal(int connectionMaxTotal) {
		this.connectionMaxTotal = connectionMaxTotal;
	}

	public int getConnectionMaxPerHost() {
		return connectionMaxPerHost;
	}

	public void setConnectionMaxPerHost(int connectionMaxPerHost) {
		this.connectionMaxPerHost = connectionMaxPerHost;
	}

	/**
	 * @param codeing
	 *            the codeing to set
	 */
	public void setCodeing(String codeing) {
		this.codeing = codeing;
	}

	/**
	 * @return the codeing
	 */
	public String getCodeing() {
		return codeing;
	}

	public void setSoTimeOut(int soTimeOut) {
		this.soTimeOut = soTimeOut;
	}

	public int getSoTimeOut() {
		return soTimeOut;
	}
}
