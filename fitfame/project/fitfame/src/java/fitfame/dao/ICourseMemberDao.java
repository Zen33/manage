/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.CourseMember;

/**
 * @author zhangshu
 *
 */
public interface ICourseMemberDao {

	public List<CourseMember> getCourseMemberList(long cid);
	
	public void insertCourseMember(CourseMember member);
	
	public void deleteCourseMember(CourseMember member);
}
