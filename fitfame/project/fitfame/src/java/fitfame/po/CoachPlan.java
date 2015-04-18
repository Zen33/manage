/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class CoachPlan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6987062040231354289L;
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

}
