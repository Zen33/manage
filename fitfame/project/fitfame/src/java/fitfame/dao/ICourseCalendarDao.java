/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.common.page.PageInfo;
import fitfame.po.CourseCalendar;

/**
 * @author zhangshu
 *
 */
public interface ICourseCalendarDao {

	public CourseCalendar getCourseCalendar(long id);
	
	public CourseCalendar getPreviousCourseCalendar(String cid, long data);
	
	public CourseCalendar getNextCourseCalendar(String cid, long date);
	
	public List<CourseCalendar> getMonthCourseCalendar(String cid, int month);
	
	public List<CourseCalendar> getUndoCourseCalendar(String uid, long date, PageInfo page);
	
	public List<CourseCalendar> getDoneCourseCalendar(String uid, long date, PageInfo page);
	
	public int updateCourseCalendar(CourseCalendar info);
	
	public int insertCourseCalendar(CourseCalendar info);
	
	public int deleteCourseCalendar(long id);
	
	public int getCourseNum(long tid, String cid);
	
	public List<CourseCalendar> getCourseCalendars(long tid, String cid);
}
