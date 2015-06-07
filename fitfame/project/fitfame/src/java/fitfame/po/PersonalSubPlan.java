/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class PersonalSubPlan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3796293970335286440L;
	// id
	private long id;
	// 个人计划id
	private long ppid;
	// 序号
	private long rank;
	// 开始时间
	private long sdate = -1;
	// 完成日期
	private long edate = -1;
	// 完成到第几步训练
	private int step = -1;
	
	public void setDefaultValue()
	{
		this.sdate = 0;
		this.edate = 0;
		this.step = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public long getRank() {
		return rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

	public long getPpid() {
		return ppid;
	}

	public void setPpid(long ppid) {
		this.ppid = ppid;
	}
}
