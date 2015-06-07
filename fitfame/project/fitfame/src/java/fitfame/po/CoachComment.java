/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class CoachComment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8726920075152241621L;
	private long id;
	// 用户id
	private String uid;
	// 教练id
	private String cid;
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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

	public void setIfead(int read) {
		this.ifread = read;
	}

	public long getCdate() {
		return cdate;
	}

	public void setCdate(long cdate) {
		this.cdate = cdate;
	}

}
