package fitfame.netty.example.server;

import static org.jboss.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import fitfame.security.utils.AesUtils;
import fitfame.security.utils.FakeHelper;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.Cookie;
import org.jboss.netty.handler.codec.http.CookieDecoder;
import org.jboss.netty.handler.codec.http.CookieEncoder;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpChunkTrailer;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.util.CharsetUtil;

public class HttpSecuritySeverHandler extends SimpleChannelUpstreamHandler {

	private HttpRequest request;
	private boolean readingChunks;
	private final StringBuffer buf = new StringBuffer();

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		if (!readingChunks) {
			HttpRequest request = this.request = (HttpRequest) e.getMessage();
			
			FakeHelper.serverHandleRequest(request);
			if (is100ContinueExpected(request)) {
				send100Continue(e);
			}
			
			buf.setLength(0);
			
			//buf.append(RSAUtils.encryptByPrivateKey("rsa from server", RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY)));
			//buf.append("&aes=");
			buf.append(AesUtils.encrypt("aes from server"));

			writeResponse(e);
			/*buf.setLength(0);
			buf.append("welcome to the wild wild web server\r\n");
			buf.append("===================================\r\n");
			buf.append("VERSION: " + request.getProtocolVersion() + "\r\n");
			buf.append("HOSTNAME: " + HttpHeaders.getHost(request, "unknown")
					+ "\r\n");
			buf.append("REQUEST_URI: " + request.getUri() + "\r\n\r\n");

			for (Map.Entry<String, String> h : request.getHeaders()) {
				buf.append("HEADER: " + h.getKey() + " = " + h.getValue()
						+ "\r\n");
			}
			buf.append("\r\n");

			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(
					request.getUri());
			Map<String, List<String>> params = queryStringDecoder
					.getParameters();
			if (!params.isEmpty()) {
				for (Entry<String, List<String>> p : params.entrySet()) {
					String key = p.getKey();
					List<String> vals = p.getValue();
					for (String val : vals) {
						buf.append("PARAM: " + key + " = " + val + "\r\n");
					}
				}
				buf.append("\r\n");
			}

			if (request.isChunked()) {
				readingChunks = true;
			} else {
				ChannelBuffer content = request.getContent();
				if (content.readable()) {
					buf.append("CONTENT: "
							+ content.toString(CharsetUtil.UTF_8) + "\r\n");
				}
				writeResponse(e);
			}*/
		} else {
			HttpChunk chunk = (HttpChunk) e.getMessage();
			if (chunk.isLast()) {
				readingChunks = false;
				buf.append("END OF CONTENT \r\n");
				HttpChunkTrailer trailer = (HttpChunkTrailer) chunk;
				if (!trailer.getHeaderNames().isEmpty()) {
					buf.append("\r\n");
					for (String name : trailer.getHeaderNames()) {
						for (String value : trailer.getHeaders(name)) {
							buf.append("TRAILING HEADER: " + name + " = "
									+ value + "\r\n");
						}
					}
					buf.append("\r\n");
				}
				writeResponse(e);
			} else {
				buf.append("CHUNK: "
						+ chunk.getContent().toString(CharsetUtil.UTF_8)
						+ "\r\n");
			}
		}

	}

	private void send100Continue(MessageEvent e) {
		HttpResponse response = new DefaultHttpResponse(HTTP_1_1,
				HttpResponseStatus.CONTINUE);
		e.getChannel().write(response);
	}

	private void writeResponse(MessageEvent e) {
		boolean keepalive = isKeepAlive(request);

		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
		response.setContent(ChannelBuffers.copiedBuffer(buf.toString(),
				CharsetUtil.UTF_8));
		response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");

		if (keepalive) {
			response.setHeader(CONTENT_LENGTH, response.getContent()
					.readableBytes());
			response.setHeader(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
		}

		String cookieString = request.getHeader(HttpHeaders.Names.COOKIE);
		if (cookieString != null) {
			CookieDecoder cookieDecoder = new CookieDecoder();
			Set<Cookie> cookies = cookieDecoder.decode(cookieString);
			if (!cookies.isEmpty()) {
				CookieEncoder cookieEncoder = new CookieEncoder(true);
				for (Cookie cookie : cookies) {
					cookieEncoder.addCookie(cookie);
					response.addHeader(HttpHeaders.Names.SET_COOKIE,
							cookieEncoder.encode());
				}
			}
		} else {
			CookieEncoder cookieEncoder = new CookieEncoder(true);
            cookieEncoder.addCookie("key1", "value1");
            response.addHeader(HttpHeaders.Names.SET_COOKIE, cookieEncoder.encode());
            cookieEncoder.addCookie("key2", "value2");
            response.addHeader(HttpHeaders.Names.SET_COOKIE, cookieEncoder.encode());
		}

		ChannelFuture future = e.getChannel().write(response);

		if (!keepalive) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext arg0, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
		
	}

}
