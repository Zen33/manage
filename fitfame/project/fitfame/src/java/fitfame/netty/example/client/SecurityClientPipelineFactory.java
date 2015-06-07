package fitfame.netty.example.client;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpClientCodec;
import org.jboss.netty.handler.codec.http.HttpContentDecompressor;

public class SecurityClientPipelineFactory implements ChannelPipelineFactory {

	private final boolean ssl;

	public SecurityClientPipelineFactory(boolean ssl) {
		this.ssl = ssl;
	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("codec", new HttpClientCodec());
		pipeline.addLast("inflater", new HttpContentDecompressor());
		pipeline.addLast("handler", new SecurityClientHandler());
		return pipeline;
	}

}
