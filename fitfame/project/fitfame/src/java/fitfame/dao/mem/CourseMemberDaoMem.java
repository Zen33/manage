/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.dao.ICourseMemberDao;
import fitfame.po.CourseMember;

/**
 * @author zhangshu
 *
 */
@Repository
public class CourseMemberDaoMem implements ICourseMemberDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICourseMemberDao#getCourseMemberList(long)
	 */
	@Override
	public List<CourseMember> getCourseMemberList(long cid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICourseMemberDao#insertCourseMember(fitfame.po.CourseMember)
	 */
	@Override
	public void insertCourseMember(CourseMember member) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICourseMemberDao#deleteCourseMember(fitfame.po.CourseMember)
	 */
	@Override
	public void deleteCourseMember(CourseMember member) {
		// TODO Auto-generated method stub

	}

}
