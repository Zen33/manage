/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.common.page.PageInfo;
import fitfame.dao.ICourseCalendarDao;
import fitfame.po.CourseCalendar;

/**
 * @author zhangshu
 *
 */
@Repository
public class CourseCalendarDaoMem implements ICourseCalendarDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICourseCalendarDao#getMonthCourseCalendar(java.lang.String, int)
	 */
	@Override
	public List<CourseCalendar> getMonthCourseCalendar(String cid, int month) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICourseCalendarDao#getUndoCourseCalendar(java.lang.String, long, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<CourseCalendar> getUndoCourseCalendar(String uid, long date,
			PageInfo page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICourseCalendarDao#getDoneCourseCalendar(java.lang.String, long, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<CourseCalendar> getDoneCourseCalendar(String uid, long date,
			PageInfo page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICourseCalendarDao#updateCourseCalendar(fitfame.po.CourseCalendar)
	 */
	@Override
	public int updateCourseCalendar(CourseCalendar info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICourseCalendarDao#insertCourseCalendar(fitfame.po.CourseCalendar)
	 */
	@Override
	public int insertCourseCalendar(CourseCalendar info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICourseCalendarDao#deleteCourseCalendar(long)
	 */
	@Override
	public int deleteCourseCalendar(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CourseCalendar getCourseCalendar(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CourseCalendar getPreviousCourseCalendar(String cid, long data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CourseCalendar getNextCourseCalendar(String cid, long date) {
		// TODO Auto-generated method stub
		return null;
	}

}
