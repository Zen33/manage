/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class CoachService implements Serializable {

	private static final long serialVersionUID = -4094520273888756486L;
	// 服务id
	private long sid;
	// 教练id
	private String cid;
	// 服务名称
	private String name;
	// 服务介绍
	private String intro;
	// 花费
	private int cost = -1;
	// 时长
	private int month = -1;
	// 线上服务次数
	private int online = -1;
	// 线下服务次数
	private int offline = -1;
	// 购买人数
	private int num = -1;
	// 线上服务时长
	private int online_times = -1;
	// 线下服务时长
	private int offline_times = -1;
		// 是否在用0在用1不在用
	private int inuse = -1;

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
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

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public int getOffline() {
		return offline;
	}

	public void setOffline(int offline) {
		this.offline = offline;
	}

	public int getOnline_times() {
		return online_times;
	}

	public void setOnline_times(int online_times) {
		this.online_times = online_times;
	}

	public int getOffline_times() {
		return offline_times;
	}

	public void setOffline_times(int offline_times) {
		this.offline_times = offline_times;
	}

	public int getInuse() {
		return inuse;
	}

	public void setInuse(int inuse) {
		this.inuse = inuse;
	}
}
