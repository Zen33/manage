/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class SubPlanDesc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4722304036528362085L;
	private long id;
	//创建的用户id
	private String cid;
	//名称
	private String name;
	// 介绍
	private String intro;
	// 缩略图
	private String pic;
	// 教学地址
	private String url;
	// 教学地址类型0图片1视频
	private int category = -1;
	//锻炼数量
	private int  quantity = -1;
	//单位
	private String  units;     
	// 共享类型0共享1不共享
	private int share = -1;
	// 休息时间（分钟）
	private int duration = -1;
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

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public long getRank() {
		return rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
