package fitfame.common.http;

import java.net.URL;
import fitfame.common.exception.NetException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
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
	private static int DEFAULT_CONNECTIONS_MAX_PERHOST = 200;
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
				throw new HttpException("Connection Error!return Status :"
						+ gm.getStatusCode());
			}
			result = gm.getResponseBodyAsString();
		} catch (Exception e) {
			//throw new NetException("HttpClient catch!", e);
			throw new NetException(e.toString(), e);
		} finally {
			gm.releaseConnection();
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
	public String getContextByPostMethod(String url, List<KeyValue> params) {
		HttpClient client = getHttpClient();
		// 设置编码
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				codeing);
		PostMethod post = null;
		String result = "";
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
			result = post.getResponseBodyAsString();
		} catch (Exception e) {
			throw new NetException("HttpClient catch!", e);
		} finally {
			if (post != null)
				post.releaseConnection();
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
