/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class StateInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7956973700428251337L;
	// 用户id
	private String uid;
	// 新回复数
	private int reply = -1;
	// 新评论数
	private int comment = -1;
	
	public void setDefaultValue()
	{
		this.reply = 0;
		this.comment = 0;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

}
