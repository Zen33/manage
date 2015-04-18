package fitfame.common.action;

import org.apache.log4j.Logger;

/**
 *@comment:定义ACTION基类
 *@date 2011-10-31
 *@author Administrator
 *@since 1.0
 */
public class BaseAction {
	/** 日志 */
	protected Logger logger = Logger.getLogger(this.getClass());
	
	/** 返回关键字 */
	protected final static String RETURN_KEY_STATUS = "status";
	protected final static String RETURN_KEY_ITEMS = "items";
	protected final static String RETURN_KEY_PAGE = "page";
	
	/** 常用参数 */
	protected final static String PARAMETER_UID = "uid";
}
