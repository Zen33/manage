/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class FriendInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1139757705097204080L;
	// 用户1id
	private String uid1;
	// 用户2id
	private String uid2;

	public String getUid1() {
		return uid1;
	}

	public void setUid1(String uid1) {
		this.uid1 = uid1;
	}

	public String getUid2() {
		return uid2;
	}

	public void setUid2(String uid2) {
		this.uid2 = uid2;
	}
}
