/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class PersonalPlan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1672711111613317696L;
	private long id;
	// 用户id
	private String uid;
	// 计划id
	private long pid;
	// 开始日期
	private long sdate = -1;
	// 完成日期
	private long edate = -1;

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

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
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


}
