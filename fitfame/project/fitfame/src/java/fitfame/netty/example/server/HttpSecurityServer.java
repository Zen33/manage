package fitfame.netty.example.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class HttpSecurityServer {

	public void start() {
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		
		bootstrap.setPipelineFactory(new HttpSecurityServerPipelineFactory());
		bootstrap.bind(new InetSocketAddress(80));
	}

	public static void main(String[] args) {
		
		new HttpSecurityServer().start();

	}

}
