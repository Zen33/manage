package fitfame.common.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

public class PropertyFile{
	static Logger logger = Logger.getLogger(PropertyFile.class); 
	//config.propertites
	public static Properties p = new Properties();
	//oauth认证路径
	public static String oAuthUrl = "";
	//asset.season.properties
	public static Properties pAssetSeason = new Properties();
	//asset.year.properties
	public static Properties pAssetYear = new Properties();
	//cash.season.properties
	public static Properties pCashSeason = new Properties();
	//cash.year.properties
	public static Properties pCashYear = new Properties();
	//key.season.properties
	public static Properties pKeySeason = new Properties();
	//key.year.properties
	public static Properties pKeyYear = new Properties();
	//profit.season.properties
	public static Properties pProfitSeason = new Properties();
	//profit.year.properties
	public static Properties pProfitYear = new Properties();
	public static Properties pDbFieldMap = new Properties();
	
	static{
		logger.info("加载上下文配置文件");
		//读取配置文件
		ClassPathResource context1 = new ClassPathResource("config/config.properties");
		ClassPathResource context2 = new ClassPathResource("config/asset.season.properties");
		ClassPathResource context3 = new ClassPathResource("config/asset.year.properties");
		ClassPathResource context4 = new ClassPathResource("config/cash.season.properties");
		ClassPathResource context5 = new ClassPathResource("config/cash.year.properties");
		ClassPathResource context6 = new ClassPathResource("config/key.season.properties");
		ClassPathResource context7 = new ClassPathResource("config/key.year.properties");
		ClassPathResource context8 = new ClassPathResource("config/profit.season.properties");
		ClassPathResource context9 = new ClassPathResource("config/profit.year.properties");
		ClassPathResource context10 = new ClassPathResource("config/dbmap.properties");
		
		
		try {
			//封装property属性类
			p.load(context1.getInputStream());
			pAssetSeason.load(context2.getInputStream());
			pAssetYear.load(context3.getInputStream());
			pCashSeason.load(context4.getInputStream());
			pCashYear.load(context5.getInputStream());
			pKeySeason.load(context6.getInputStream());
			pKeyYear.load(context7.getInputStream());
			pProfitSeason.load(context8.getInputStream());
			pProfitYear.load(context9.getInputStream());
			pDbFieldMap.load(context10.getInputStream());
		} catch (IOException e) {
		}
		
		//获取oauth验证路径
		oAuthUrl = p.getProperty("oauth.url");
	
		
	}

}
