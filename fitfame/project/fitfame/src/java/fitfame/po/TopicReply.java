/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class TopicReply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1884182566516722678L;
	private long id;
	// 用户id
	private String uid;
	// 回复的动态id
	private long totid;
	// 回复的用户id
	private String touid;
	// 内容
	private String content;
	// 是否已读0已读1未读
	private int ifread = -1;
	// 发布日期
	private long cdate = -1;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public long getTotid() {
		return totid;
	}

	public void setTotid(long totid) {
		this.totid = totid;
	}

	public String getTouid() {
		return touid;
	}

	public void setTouid(String touid) {
		this.touid = touid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIfread() {
		return ifread;
	}

	public void setIfread(int read) {
		this.ifread = read;
	}

	public long getCdate() {
		return cdate;
	}

	public void setCdate(long cdate) {
		this.cdate = cdate;
	}

}
