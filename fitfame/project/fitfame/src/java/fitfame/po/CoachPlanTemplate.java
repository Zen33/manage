/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class CoachPlanTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8019517590520586956L;
	// 计划id
	private long pid;
	// 教练id
	private String cid;
	// 计划名称
	private String name;
	// 计划介绍
	private String intro;
	// 计划图标地址
	private String icon;
	// 计划总时长
	private int duration = -1;
	// 是否在用0在用1不在用
	private int inuse = -1;

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

	public int getInuse() {
		return inuse;
	}

	public void setInuse(int inuse) {
		this.inuse = inuse;
	}
}
