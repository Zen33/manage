package fitfame.common.vo;

import java.io.Serializable;

/**
 *@comment:定义VO基类
 *@date 2011-10-31
 *@author Administrator
 *@since 1.0
 */
public class BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4134475891448039959L;

	protected String uid;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
