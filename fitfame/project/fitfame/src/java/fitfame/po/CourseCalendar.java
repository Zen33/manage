/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 *
 */
public class CourseCalendar implements Serializable {

	private static final long serialVersionUID = 8484947243937506427L;

	private long id;
	//教练id 
	private String cid;
	//服务id
	private long sid;
	//服务类型（线上0线下1）
	private int stype;
	//授课日期 201503160000年月日时分
	private long cdate;
	//授课 时长（分钟）
	private int minutes;
	//是否发布（0发布1不发布）
	private int published=-1;
	//授课人数上限
	private int maxlimit=-1;
	//已报名人数
	private int sign=-1;
	
	private String intro;
	
	private long tid;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public int getStype() {
		return stype;
	}
	public void setStype(int stype) {
		this.stype = stype;
	}
	public long getCdate() {
		return cdate;
	}
	public void setCdate(long cdate) {
		this.cdate = cdate;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getPublished() {
		return published;
	}
	public void setPublished(int published) {
		this.published = published;
	}
	public int getMaxlimit() {
		return maxlimit;
	}
	public void setMaxlimit(int maxlimit) {
		this.maxlimit = maxlimit;
	}
	public int getSign() {
		return sign;
	}
	public void setSign(int sign) {
		this.sign = sign;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public long getTid() {
		return tid;
	}
	public void setTid(long tid) {
		this.tid = tid;
	}
}
