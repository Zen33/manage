/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.common.page.PageInfo;
import fitfame.dao.ICoachCommentDao;
import fitfame.po.CoachComment;

/**
 * @author zhangshu
 *
 */
@Repository
public class CoachCommentDaoMem implements ICoachCommentDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachCommentDao#getCoachCommentByCid(java.lang.String, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<CoachComment> getCoachCommentByCid(String cid, PageInfo page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachCommentDao#getUnreadCoachCommentByCid(java.lang.String, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<CoachComment> getUnreadCoachCommentByCid(String cid,
			PageInfo page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachCommentDao#insertCoachComment(fitfame.po.CoachComment)
	 */
	@Override
	public int insertCoachComment(CoachComment comment) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachCommentDao#updateCoachComment(fitfame.po.CoachComment)
	 */
	@Override
	public int updateCoachComment(CoachComment comment) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachCommentDao#deleteCoachComment(long)
	 */
	@Override
	public int deleteCoachComment(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
