package fitfame.netty.example.client;

import fitfame.security.utils.AesUtils;
import fitfame.security.utils.RSAUtils;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;

public class SecurityClientHandler extends SimpleChannelUpstreamHandler{
	
	private boolean readingChunk;
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
			if(!readingChunk){
				HttpResponse response = (HttpResponse) e.getMessage();
				
				System.out.println("Status: " + response.getStatus());
				System.out.println("version: " + response.getProtocolVersion());
				System.out.println();
				
				if(!response.getHeaderNames().isEmpty()){
					for(String name : response.getHeaderNames()){
						for(String value: response.getHeaders(name)){
							System.out.println("Header: " + name + " = " + value);
						}
					}
					
					System.out.println();
				}
				
				if(response.isChunked()){
					readingChunk = true;
					System.out.println("chunked content{");
				}else{
					ChannelBuffer content = response.getContent();
					if(content.readable()){
						System.out.println("CONTENT{");
						System.out.println(content.toString(CharsetUtil.UTF_8));
						System.out.println("}End of content");
					}
				}
			}else{
				HttpChunk chunk = (HttpChunk) e.getMessage();
				if(chunk.isLast()){
					readingChunk = false;
					System.out.println("} END OF CHUNKED CONTENT");
				}else{
					System.out.println(chunk.getContent().toString(CharsetUtil.UTF_8));
					
					String str = chunk.getContent().toString(CharsetUtil.UTF_8);
					String [] values = str.split("&");
					String rsa = values[0].substring("rsa=".length());
					System.out.println("rsa decrypted :" + RSAUtils.decryptByPublicKey(rsa, RSAUtils.loadPublicKey(RSAUtils.PUBLIC_KEY)));
					String aes = values[1].substring("aes=".length());
					System.out.println("aes decrypted :" + AesUtils.decrypt(aes));
					
					System.out.flush();
				}
				
			}
	
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext arg0, ExceptionEvent arg1)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(arg0, arg1);
	}

}
