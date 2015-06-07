/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 *
 */
public class CourseMember implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5467378426130543842L;
	
	//CourseCalendar的id
	private long cid;
	//用户id
	private String uid;
	public long getCid() {
		return cid;
	}
	public void setCid(long cid) {
		this.cid = cid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
}
