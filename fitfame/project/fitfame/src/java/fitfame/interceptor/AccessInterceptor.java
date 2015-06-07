/*
 * copywrite 2011-2012 深圳证券信息有限公司
 * 不能修改和删除上面的版权声明
 * 此代码属于本公司编写，在未经允许的情况下不得传播复制
 */
package fitfame.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import fitfame.common.util.Constant;
import fitfame.common.util.HttpConnectionClient;
import fitfame.common.util.PropertyFile;
import fitfame.common.util.ReturnCode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import net.oauth.OAuth;
import net.oauth.OAuth.Parameter;
import net.oauth.client.ExcerptInputStream;
import net.oauth.http.HttpMessage;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import common.Logger;

/**
 * Oauth拦截器,访问会进行Oauth1.0a验证
 * 
 * @author xiaojunhe,<a href="mailto:ccfeeling@gmail.com">chenglin</a>
 * 
 */
@Provider
@ServerInterceptor
public class AccessInterceptor implements PreProcessInterceptor {

	static final String GET = "GET";
	static final String POST = "POST";
	static final String PUT = "PUT";
	static final String DELETE = "DELETE";
	// 日志文件
	private final Logger log = Logger.getLogger(this.getClass());

	static {
		Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
	}

	ServerResponse response ;
	HttpMethod httpMethod;
	
	@Context
	private HttpServletRequest serletReq;

	@Override
	public ServerResponse preProcess(HttpRequest request,
			ResourceMethod reMethod){
		//查询访问者IP		
		String ip = serletReq.getRemoteAddr();
		if (serletReq.getHeader("x-forwarded-for") != null) {
			ip = serletReq.getHeader("x-forwarded-for");
		}
		//匹配不用检测的IP段
//		String regexs = PropertyFile.p.getProperty("oauth.exception");
//		for(String gex : regexs.split("#")){
//			if(ip.matches(gex)) return null;
//		}
//		
//		//临时将用户接口跳过oauth检测，正式上线后改掉此例外
//		String requestUrl = request.getUri().getRequestUri().getPath();
//		if(requestUrl.contains("/user")||requestUrl.contains("/reminder/sysmsg/page")||requestUrl.contains("/reminder/sysmsg/add")
//				||requestUrl.contains("/accessrecord/queryAccessRecord")||requestUrl.contains("/reminder/sysmsg/query")||requestUrl.contains("/public-opinion/report/detail")){
//			return null;
//		}
		//处理注册
		String registUrl = request.getUri()
		.getPath().toString();


		
		try {
			// 获取oauth验证路径
			final String url = PropertyFile.oAuthUrl;

			// 获取请求方式，目前只能处理GET和POST两种
			final String method = request.getHttpMethod();
			final boolean isDelete = DELETE.equalsIgnoreCase(method);
			final boolean isPost = POST.equalsIgnoreCase(method);
			final boolean isPut = PUT.equalsIgnoreCase(method);

			// 组建client，用于进行oauth请求
			URL u = new URL(url);
			HttpConnectionClient collectionClient = new HttpConnectionClient();
			HttpClient client = collectionClient.getClient();
			client.getHostConfiguration().setHost(u.getHost(),
					u.getPort() == -1 ? u.getDefaultPort() : u.getPort(),
					u.getProtocol());

			// 组装参数
			String urlnew = "";
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			List<Parameter> paraList = new ArrayList<Parameter>();

			// 如果是GET请求，组装查询参数
			for (Map.Entry<String, List<String>> entry : request.getUri()
					.getQueryParameters().entrySet()) {
				String name = entry.getKey();
				for (String value : entry.getValue()) {
					// 获取参数值
					Parameter map = new Parameter(name, value);
					paraList.add(map);
					list.add(new NameValuePair(name, value));
				}
			}
			urlnew = OAuth.addParameters(url, paraList);

			for (Map.Entry<String, List<String>> entry : request.getFormParameters().entrySet()) {
				String name = entry.getKey();
				for (String value : entry.getValue()) {
					//获取参数值
					Parameter map=new Parameter(name, value);
					paraList.add(map);
					list.add(new NameValuePair(name, value));
				}
			}
			MultivaluedMap<String, String> map = request.getFormParameters();
			System.out.println("00000000000===="+map);


			StringBuffer sb = new StringBuffer();
			String key = null;
			boolean first = true;
			Set<String> skey = map.keySet();
			        for (Iterator it = skey.iterator(); it.hasNext();){
			if(first){
			first = false;
			}else{
			sb.append("&");
			}
			key = (String)it.next();
			sb.append(key).append("=").append(map.get(key).get(0));
			}
			System.out.println("11111111111111111==="+sb.toString());


			
			//final InputStream body = request.getInputStream();
			// 获取Post过来的参数sb.toString()
			final byte[] bodytemp = "".getBytes();			                  
			
			byte[] excerpt = null;

			if (isPost || isPut) {
				EntityEnclosingMethod entityEnclosingMethod = isPost ? new PostMethod(
						url) : new PutMethod(url);
				//if (body != null) {
					ExcerptInputStream e = new ExcerptInputStream(
							new ByteArrayInputStream(bodytemp));
					String length = request.getHttpHeaders()
							.getRequestHeader(HttpMessage.CONTENT_LENGTH)
							.get(0);
					entityEnclosingMethod
							.setRequestEntity((length == null) ? new InputStreamRequestEntity(
									e) : new InputStreamRequestEntity(e, Long
									.parseLong(length)));
					excerpt = e.getExcerpt();
				//}
				httpMethod = entityEnclosingMethod;
			} else if (isDelete) {
				httpMethod = new DeleteMethod(urlnew);
			} else {
				httpMethod = new GetMethod(urlnew);
			}
			// 处理header信息
			MultivaluedMap<String, String> headerMap = request.getHttpHeaders()
					.getRequestHeaders();
			if (headerMap != null) {
				for (String Key : headerMap.keySet()) {
					 log.debug("key : " + Key);
					 log.debug("value : " +headerMap.get(Key).toString());
					httpMethod.addRequestHeader(Key, headerMap.get(Key).get(0));
				}
			}

			NameValuePair[] nvp = list.toArray(new NameValuePair[list.size()]);

			HttpMethodParams pp;
			if (isPost) {
				if (list.size() != 0) {
					PostMethod hm = (PostMethod) httpMethod;
					hm.setRequestBody(nvp);
				}
			}
			if (isPut) {
				if (list.size() != 0) {
					PutMethod hm = (PutMethod) httpMethod;
					RequestEntity re = null;
					hm.setRequestEntity(re);
				}
			}
			httpMethod.addRequestHeader("surl", request.getUri()
					.getAbsolutePath().toString());

			client.executeMethod(httpMethod);
			log.debug("staus:" + httpMethod.getStatusCode());
			int status = httpMethod.getStatusCode();
			//request.setInputStream(body);
			if (status == 200) {
				String json=new String (httpMethod.getResponseBodyAsString().getBytes(),"utf-8");
				log.debug("oauth proxy return body:"+json);
				serletReq.setAttribute(Constant.KEY_OAUTH, json);
				httpMethod.releaseConnection();
				return null;
			} else {
				response = new ServerResponse();
				response.setStatus(status);
				Header[] b = httpMethod.getRequestHeaders();
				for(Header bb:b){
					log.debug("Header name:"+bb.getName()+" value:"+bb.getValue());
				}
				httpMethod.releaseConnection();
				return response;
			}
			
		} catch (MalformedURLException e) {
			log.error(e);
			throw new WebApplicationException(e,Integer.parseInt(ReturnCode.SERVER_ERROR));
		} catch (HttpException e) {
			log.error(e);
			throw new WebApplicationException(e,Integer.parseInt(ReturnCode.SERVER_ERROR));
		} catch (IOException e) {
			log.error(e);
			throw new WebApplicationException(e,Integer.parseInt(ReturnCode.SERVER_ERROR));
		} 
	}

}
