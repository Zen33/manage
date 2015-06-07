/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 *
 */
public class RelationPlanAndSplan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4487496233907902749L;

	private long id;
	//计划id
	private long pid = -1;
	
	//子计划id
	private long spid = -1;
	
	//计划序号 
	private long rank = -1;
	
	//计划间隔时长
	private long duration = -1;

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public long getSpid() {
		return spid;
	}

	public void setSpid(long spid) {
		this.spid = spid;
	}

	public long getRank() {
		return rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
