/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class CoachInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3102401667282396242L;
	// 教练id
	private String cid;
	// 个人介绍
	private String intro;
	// 群号
	private int circle = -1;
	// 点赞数
	private int fav = -1;
	// 执教数
	private int exp = -1;
	//评论数
	private int reply = -1;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getCircle() {
		return circle;
	}

	public void setCircle(int circle) {
		this.circle = circle;
	}

	public int getFav() {
		return fav;
	}

	public void setFav(int fav) {
		this.fav = fav;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}
}
