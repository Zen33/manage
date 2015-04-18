package fitfame.netty.example.client;

import java.net.InetSocketAddress;
import java.net.URI;
import fitfame.security.utils.FakeHelper;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpRequest;

public class SecurityClient {

	private final URI uri;

	public SecurityClient(URI uri) {
		this.uri = uri;
	}

	public void run() {
		String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
		String host = uri.getHost() == null ? "localhost" : uri.getHost();
		int port = uri.getPort();
		

		if (!scheme.equalsIgnoreCase("http")
				&& !scheme.equalsIgnoreCase("https")) {
			System.out.println("Only http(s) is supported!");
			return;
		}

		boolean ssl = scheme.equalsIgnoreCase("https");
		ClientBootstrap bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));

		bootstrap.setPipelineFactory(new SecurityClientPipelineFactory(ssl));
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(host,
				port));
		Channel channel = future.awaitUninterruptibly().getChannel();
		if (!future.isSuccess()) {
			future.getCause().printStackTrace();
			bootstrap.releaseExternalResources();
			return;
		}

//		HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,
//				HttpMethod.GET, uri.getRawPath());
//		request.setHeader(HttpHeaders.Names.HOST, host);
//		request.setHeader(HttpHeaders.Names.CONNECTION,
//				HttpHeaders.Values.CLOSE);
//		request.setHeader(HttpHeaders.Names.ACCEPT_ENCODING,
//				HttpHeaders.Values.GZIP);
//
//		CookieEncoder httpCookieEncoder = new CookieEncoder(false);
//		httpCookieEncoder.addCookie("my-cookie", "foo");
//		httpCookieEncoder.addCookie("another-cookie", "bar");
//		request.setHeader(HttpHeaders.Names.COOKIE, httpCookieEncoder.encode());

		HttpRequest request= FakeHelper.register();
		channel.write(request);
		channel.getCloseFuture().awaitUninterruptibly();
		bootstrap.releaseExternalResources();
	}

	public static void main(String[] args) {
		URI uri = URI.create("http://localhost/register");
		new SecurityClient(uri).run();
	}

}
