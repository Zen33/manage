/*
 * 
 * 不能修改和删除上面的版权声明
 *  
 */
package fitfame.common.util;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.core.io.ClassPathResource;

/**
 * @comment:读取系统property配置文件
 * @date 2011-11-8
 * @author zs
 * @since 1.0
 */
public class PropertyFileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2873819802540867116L;
	//config.propertites
	public static Properties p = new Properties();
	//oauth认证路径
	public static String oAuthUrl = "";

	
	/**
	 * 初始化类，用于读取各配置文件，并写入到相应的静态MAP中
	 */
	@Override
	public void init() throws ServletException {
		//读取配置文件
		ClassPathResource context1 = new ClassPathResource("config/config.properties");
		
		try {
			//封装property属性类
			p.load(context1.getInputStream());
		} catch (IOException e) {
			throw new ServletException(e);
		}
		
		//获取oauth验证路径
		oAuthUrl = p.getProperty("oauth.url");
	}

	@Override
	public void destroy() {
		super.destroy();
		p = null;
	}
	
	

}
