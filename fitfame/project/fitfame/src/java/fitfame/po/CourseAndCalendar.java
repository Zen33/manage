/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 *
 */
public class CourseAndCalendar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6837885419043655409L;

	private CoachService service;
	
	private CourseCalendar calendar;

	public CoachService getService() {
		return service;
	}

	public void setService(CoachService service) {
		this.service = service;
	}

	public CourseCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(CourseCalendar calendar) {
		this.calendar = calendar;
	}
}
