/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu 用户登录信息
 */
public class UserPassport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6559794151487560558L;
	// 用户id
	private String uid;
	// 用户密码
	private String pw;
	// 用户电话
	private String tel;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
