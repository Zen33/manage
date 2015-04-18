package fitfame.common.dao;

import fitfame.common.dao.ibatis.IBatisEntityDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ibatis.sqlmap.client.SqlMapClient;
import common.Logger;
/**
 *@comment:基类DAO
 *@date 2011-11-10
 *@author zs
 * @param <T>
 *@since 1.0
 */
public class BaseDAO<T> extends IBatisEntityDao<T> {
	
	protected static Logger logger = Logger.getLogger(IBatisEntityDao.class);

	/**
	 * 由 spring扫描的component，把id为sqlMapClient_fitfame自动注入
	 * @param sqlMapClient
	 */
    @Autowired 
    public void setSqlMapClientBase(@Qualifier("sqlMapClient_fitfame") SqlMapClient sqlMapClient) {  
   
    	super.setSqlMapClient(sqlMapClient);  
    }  

}
