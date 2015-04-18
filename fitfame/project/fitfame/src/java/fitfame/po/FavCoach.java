/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class FavCoach implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -816073317856604800L;
	// 教练id
	private String cid;
	// 用户id
	private String uid;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
