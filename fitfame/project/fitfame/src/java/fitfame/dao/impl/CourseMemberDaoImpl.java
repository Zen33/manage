/**
 * 
 */
package fitfame.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fitfame.common.dao.BaseDAO;
import fitfame.common.exception.BaseDaoException;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.SqlErrorUtil;
import fitfame.dao.ICourseMemberDao;
import fitfame.po.CourseMember;

/**
 * @author zhangshu
 *
 */
@Repository
public class CourseMemberDaoImpl extends BaseDAO<CourseMember> implements
		ICourseMemberDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICourseMemberDao#getCourseMemberList(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CourseMember> getCourseMemberList(long cid) {
		// TODO Auto-generated method stub
		List<CourseMember> result =null;
		try{
			result = (List<CourseMember>)this.getSqlMapClientTemplate().queryForList("CourseMember.getCourseMemberList", cid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getCourseMemberList error cid:"+String.valueOf(cid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICourseMemberDao#insertCourseMember(fitfame.po.CourseMember)
	 */
	@Override
	public void insertCourseMember(CourseMember member) {
		// TODO Auto-generated method stub
		try{
			this.getSqlMapClientTemplate().insert("CoachService.insertCoachService", member);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertCoachService"+SqlErrorUtil.FormCourseMemberLog(member));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICourseMemberDao#deleteCourseMember(fitfame.po.CourseMember)
	 */
	@Override
	public void deleteCourseMember(CourseMember member) {
		// TODO Auto-generated method stub
		try{
			this.getSqlMapClientTemplate().delete("CoachService.deleteCourseMember", member);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deleteCourseMember"+SqlErrorUtil.FormCourseMemberLog(member));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
	}

}
