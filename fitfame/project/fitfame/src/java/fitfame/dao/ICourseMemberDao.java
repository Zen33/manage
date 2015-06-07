/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.CourseCalendar;
import fitfame.po.CourseMember;

/**
 * @author zhangshu
 *
 */
public interface ICourseMemberDao {

	public List<CourseMember> getCourseMemberList(long cid);
	
	public List<CourseCalendar> getCourseMembers(String uid);
	
	public void insertCourseMember(CourseMember member);
	
	public void deleteCourseMember(CourseMember member);
	
	public CourseMember getCourseMember(CourseMember info);
}
