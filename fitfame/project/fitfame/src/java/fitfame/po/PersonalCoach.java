/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class PersonalCoach implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6366380900102815877L;
	// 用户id
	private String uid;
	// 教练id
	private String cid;
	// 开始日期
	private long sdate = -1;
	// 结束日期
	private long edate = -1;
	// 服务id
	private long sid;
	//服务价格
	private long price;
	
	//线上服务次数
	private long online;
	
	//线下服务次数
	private long offline;
	
	private int online_unsign;
	
	private int offline_unsign;

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

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public long getSdate() {
		return sdate;
	}

	public void setSdate(long sdate) {
		this.sdate = sdate;
	}

	public long getEdate() {
		return edate;
	}

	public void setEdate(long edate) {
		this.edate = edate;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getOnline() {
		return online;
	}

	public void setOnline(long online) {
		this.online = online;
	}

	public long getOffline() {
		return offline;
	}

	public void setOffline(long offline) {
		this.offline = offline;
	}

	public int getOnline_unsign() {
		return online_unsign;
	}

	public void setOnline_unsign(int online_unsign) {
		this.online_unsign = online_unsign;
	}

	public int getOffline_unsign() {
		return offline_unsign;
	}

	public void setOffline_unsign(int offline_unsign) {
		this.offline_unsign = offline_unsign;
	}
}
