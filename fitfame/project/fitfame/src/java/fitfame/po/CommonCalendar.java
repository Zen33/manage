/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 *
 */
public class CommonCalendar implements Serializable {

	private static final long serialVersionUID = -2952448914322157917L;
	
	private long cid;
	//授课日期 20150316
	private int cdate;
	//星期几（1-7）
	private int week;
	//是否假期
	private String holiday;
	
	public int getCdate() {
		return cdate;
	}
	public void setCdate(int cdate) {
		this.cdate = cdate;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public long getCid() {
		return cid;
	}
	public void setCid(long cid) {
		this.cid = cid;
	}
	public String getHoliday() {
		return holiday;
	}
	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}
}
