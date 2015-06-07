/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class SubPlan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8614535244300428068L;
	// 子计划id
	private long id;
	//创建的用户id
	private String cid;
	// 子计划名称
	private String name;
	// 子计划介绍
	private String intro;
	// 子计划图标地址
	private String icon;
	// 持续时间（分钟）
	private int duration = -1;
	// 共享类型0共享1不共享
	private int share = -1;
	// 是否在用0在用1不在用
	private int inuse = -1;
	// 序号 
	private long rank = -1;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public int getInuse() {
		return inuse;
	}

	public void setInuse(int inuse) {
		this.inuse = inuse;
	}

	public long getRank() {
		return rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

}
